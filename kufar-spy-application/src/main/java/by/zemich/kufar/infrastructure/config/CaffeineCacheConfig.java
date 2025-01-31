package by.zemich.kufar.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CaffeineCacheConfig {

    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("priceStatistics");
        cacheManager.setCaffeine(caffeineConfig());
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Максимальный размер кэша (количество записей)
                .expireAfterWrite(30, TimeUnit.MINUTES) // Время жизни записей после записи
                .expireAfterAccess(20, TimeUnit.MINUTES) // Удаление неиспользуемых данных
                .recordStats(); // Включаем статистику (для мониторинга)
    }

    @Bean
    public CacheManager caffeineCacheManagerForPostManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("telegramposts");
        cacheManager.setCaffeine(caffeineConfigForPostManager());
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

    public Caffeine<Object, Object> caffeineConfigForPostManager() {
        return Caffeine.newBuilder()
                .maximumSize(1_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .recordStats();
    }

}
