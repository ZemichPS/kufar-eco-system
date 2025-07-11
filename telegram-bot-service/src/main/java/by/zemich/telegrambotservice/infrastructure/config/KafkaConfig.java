package by.zemich.telegrambotservice.infrastructure.config;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableKafka
@Slf4j
@ConditionalOnProperty(
        name = "spring.kafka.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public SchemaRegistryClient schemaRegistryClient() {
        return new CachedSchemaRegistryClient(schemaRegistryUrl, 100);
    }

    @Bean
    public ConsumerFactory<String, GenericRecord> consumerFactory() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "telegram-service-group");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        consumerProps.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        consumerProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(consumerProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GenericRecord> kafkaListenerContainerFactory(
            CommonErrorHandler delegatingErrorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, GenericRecord> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(delegatingErrorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }


    @Bean
    CommonErrorHandler delegatingErrorHandler(
            KafkaTemplate<byte[], byte[]> deserializationDltTemplate,
            KafkaTemplate<String, GenericRecord> mainKafkaTemplate) {

        // 1. Основной обработчик (для всех исключений по умолчанию)
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(mainKafkaTemplate),
                new FixedBackOff(1000L, 2) // 2 попытки с интервалом 1 секунда
        );
        defaultErrorHandler.setSeekAfterError(true);

        // 2. Обработчик ошибок десериализации
        DefaultErrorHandler deserializationErrorHandler = new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(
                        deserializationDltTemplate,
                        (record, ex) -> new TopicPartition("DEAD_TOPIC.sersde.DLT", record.partition())
                ),
                new FixedBackOff(0L, 0) // Немедленно в DLQ
        );
        deserializationErrorHandler.setSeekAfterError(true);

        // 3. Создаем кастомный LoggingErrorHandler с тем же seeksAfterHandling
        CommonErrorHandler loggingErrorHandler = new CommonErrorHandler() {
            @Override
            public boolean seeksAfterHandling() {
                return true; // Должно совпадать с другими обработчиками!
            }

            @Override
            public void handleOtherException(Exception exception, Consumer<?, ?> consumer,
                                             MessageListenerContainer container, boolean batchListener) {
                // Логирование ошибки
                log.error("Kafka processing error: {}", exception.getMessage());
            }
        };

        // 4. Собираем делегирующий обработчик
        CommonDelegatingErrorHandler delegatingErrorHandler = new CommonDelegatingErrorHandler(defaultErrorHandler);
        delegatingErrorHandler.addDelegate(DeserializationException.class, deserializationErrorHandler);
        delegatingErrorHandler.addDelegate(RuntimeException.class, loggingErrorHandler);

        return delegatingErrorHandler;
    }
}