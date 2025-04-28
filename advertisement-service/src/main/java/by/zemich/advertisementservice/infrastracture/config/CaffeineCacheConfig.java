package by.zemich.advertisementservice.infrastracture.config;

import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    @Primary
    public CacheManager advertisementCaffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
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


    @Bean
    public Cache advertisementsForCommandCache(CacheManager cacheManager) {
        return cacheManager.getCache("advertisementsForCommandCache");
    }

    @Bean
    public Cache advertisementsForQueryCache(CacheManager cacheManager) {
        return cacheManager.getCache("advertisementsForQueryCache");
    }

    @Bean("advertisementQueryKeyGenerator")
    public KeyGenerator advertisementQueryKeyGenerator(ObjectMapper objectMapper) {

        return (target, method, params) -> {
            try {
                AdvertisementFilter filter = (AdvertisementFilter) params[0];
                Pageable pageable = (Pageable) params[1];

                String filterJson = objectMapper.writeValueAsString(filter);

                return "filter:" + filterJson +
                        "|page:" + pageable.getPageNumber() +
                        "|size:" + pageable.getPageSize() +
                        "|sort:" + pageable.getSort().toString();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to generate cache key", e);
            }
        };
    }
}
