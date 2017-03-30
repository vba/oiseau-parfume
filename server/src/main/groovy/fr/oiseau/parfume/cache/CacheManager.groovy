package fr.oiseau.parfume.cache

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder

import java.util.concurrent.TimeUnit

interface CacheManager<K, V> {
    V putIfNotPresent(K key, V value)
    V put(K key, V value)
    Optional<V> get(K key)
}

class PagesCacheManager implements CacheManager<String, String> {
    private @Lazy Cache<String, String> cache = CacheBuilder.newBuilder()
        .maximumSize(10000)
        .expireAfterAccess(1, TimeUnit.HOURS)
        .build();

    String putIfNotPresent(String key, String value) {
        if(cache.getIfPresent(key) != null) return value
        cache.put(key, value)
        value
    }

    String put(String key, String value) {
        cache.put(key, value)
        value
    }

    Optional<String> get(String key) {
        Optional.ofNullable(cache.getIfPresent(key));
    }
}
