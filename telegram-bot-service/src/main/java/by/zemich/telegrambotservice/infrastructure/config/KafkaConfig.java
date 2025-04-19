package by.zemich.telegrambotservice.infrastructure.config;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
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
    CommonErrorHandler delegatingErrorHandler(
            KafkaTemplate<byte[], byte[]> deserializationDltTemplate,
            KafkaTemplate<String, GenericRecord> mainKafkaTemplate
    ) {
        CommonErrorHandler defaultErrorHandler = defaultErrorHandler(mainKafkaTemplate);
        CommonDelegatingErrorHandler delegatingErrorHandler = new CommonDelegatingErrorHandler(defaultErrorHandler);
        delegatingErrorHandler.setErrorHandlers(errorHandlers(deserializationDltTemplate));
        return delegatingErrorHandler;
    }

    private LinkedHashMap<Class<? extends Throwable>, CommonErrorHandler> errorHandlers(KafkaTemplate<byte[], byte[]> deserializationDltTemplate) {
        LinkedHashMap<Class<? extends Throwable>, CommonErrorHandler> delegatesMap = new LinkedHashMap<>();
        delegatesMap.put(DeserializationException.class, serDeErrorHandler(deserializationDltTemplate));
        delegatesMap.put(RuntimeException.class, new CommonLoggingErrorHandler());
        return delegatesMap;
    }

    private CommonErrorHandler serDeErrorHandler(KafkaTemplate<byte[], byte[]> deserializationDltTemplate) {
        DeadLetterPublishingRecoverer derDeRecover = new DeadLetterPublishingRecoverer(
                deserializationDltTemplate,
                (consumerRecord, e) -> new TopicPartition(
                        String.format("%s.%s.%s", "DEAD_TOPIC", "sersde", "DLT"),
                                consumerRecord.partition()
                        ));
        return new DefaultErrorHandler(derDeRecover, new FixedBackOff(0,0));

    }

    private CommonErrorHandler defaultErrorHandler(KafkaTemplate<String, GenericRecord> mainKafkaTemplate) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(mainKafkaTemplate),
                new FixedBackOff(0, 2)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GenericRecord> kafkaListenerContainerFactory(CommonErrorHandler delegatingErrorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, GenericRecord> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setCommonErrorHandler(delegatingErrorHandler);
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.COUNT_TIME); // комитим по количеству или времени
        factory.getContainerProperties().setAckTime(5000L); // настройка времени по истечению которого commit
        factory.getContainerProperties().setAckTime(20); // количество обработанных сообщений после которого commit
        return factory;
    }


}

