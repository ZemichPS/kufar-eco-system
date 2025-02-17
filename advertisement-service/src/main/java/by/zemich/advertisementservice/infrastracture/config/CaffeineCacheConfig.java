package by.zemich.advertisementservice.infrastracture.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CaffeineCacheConfig {

    @Bean
    @Primary
    public CacheManager advertisementServiceCaffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("advertisements");
        cacheManager.setCaffeine(caffeineConfigForAdvertisementsServiceCacheManager());
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

    @Bean
    public CacheManager smartphoneAdvertisementServiceCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("marketPrices");
        cacheManager.setCaffeine(caffeineConfig());
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Максимальный размер кэша (количество записей)
                .expireAfterWrite(10, TimeUnit.MINUTES) // Время жизни записей после записи
                .expireAfterAccess(10, TimeUnit.MINUTES) // Удаление неиспользуемых данных
                .recordStats(); // Включаем статистику (для мониторинга)
    }

    public Caffeine<Object, Object> caffeineConfigForAdvertisementsServiceCacheManager() {
        return Caffeine.newBuilder()
                .maximumSize(10_000) // Максимальный размер кэша (количество записей)
                .expireAfterWrite(10, TimeUnit.MINUTES) // Время жизни записей после записи
                .expireAfterAccess(10, TimeUnit.MINUTES) // Удаление неиспользуемых данных
                .recordStats(); // Включаем статистику (для мониторинга)
    }


}
