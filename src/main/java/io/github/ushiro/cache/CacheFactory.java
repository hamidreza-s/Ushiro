package io.github.ushiro.cache;

import io.github.ushiro.cache.Cache.Strategy;

public class CacheFactory<KeyType, ValueType> {

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