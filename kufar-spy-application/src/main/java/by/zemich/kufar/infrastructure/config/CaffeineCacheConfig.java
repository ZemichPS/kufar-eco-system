package by.zemich.kufar.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("priceStatistics");
        cacheManager.setCaffeine(caffeineConfig());
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Максимальный размер кэша (количество записей)
                .expireAfterWrite(30, TimeUnit.MINUTES) // Время жизни записей после записи
                .expireAfterAccess(20, TimeUnit.MINUTES) // Удаление неиспользуемых данных
                .recordStats(); // Включаем статистику (для мониторинга)
    }


}
