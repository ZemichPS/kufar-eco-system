package by.zemich.device_catalog_service.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("brandsCache");
        cacheManager.setCaffeine(caffeineConfig());
        cacheManager.setAsyncCacheMode(false);
        return cacheManager;
    }

    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Максимальный размер кэша (количество записей)
                .expireAfterWrite(10, TimeUnit.MINUTES) // Время жизни записей после записи
                .expireAfterAccess(10, TimeUnit.MINUTES) // Удаление неиспользуемых данных
                .recordStats(); // Включаем статистику (для мониторинга)
    }

//TODO удулить если не используется
//    @Bean
//    public Cache advertisementsForCommandCache(CacheManager cacheManager) {
//        return cacheManager.getCache("brandsCache");
//    }

}

