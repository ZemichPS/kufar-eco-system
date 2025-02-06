package by.zemich.parser.application.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;


public class PostLimitedCache<K, V> {
    private final int maxSize;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<K, V> cache;

    public PostLimitedCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<>(16, 0.75f, true);
    }


    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        lock.writeLock().lock();
        try {
            V existingValue = cache.get(key);
            if (existingValue != null) {
                return existingValue;
            }
            if (cache.size() >= maxSize) {
                K oldestKey = cache.keySet().iterator().next();
                cache.remove(oldestKey);
            }
            return cache.computeIfAbsent(key, mappingFunction);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
