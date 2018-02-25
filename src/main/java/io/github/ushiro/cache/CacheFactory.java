package io.github.ushiro.cache;

import io.github.ushiro.cache.Cache.Strategy;

/**
 * A factory class for instantiating the cache object
 * based on the eviction strategy and cache table size
 * which are given as argument.
 *
 * @param <KeyType> The generic type of cache table key
 * @param <ValueType> The generic type of cache table value
  */
public class CacheFactory<KeyType, ValueType> {

    /**
     * Instantiate a new cache object based given size and strategy
     *
     * @param size The size of cache table
     * @param strategy The eviction strategy of cache table
     * @return The cache object instance
     */
    public Cache<KeyType, ValueType> newInstance(int size, Strategy strategy) {
        if (Strategy.LRU == strategy) {
            return new LRUCache<KeyType, ValueType>(size);
        } else if (Strategy.MRU == strategy) {
            return new MRUCache<KeyType, ValueType>(size);
        } else {
            throw new RuntimeException("Undefined Cache Strategy");
        }
    }
}
