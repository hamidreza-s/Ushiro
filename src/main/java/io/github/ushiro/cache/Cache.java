package io.github.ushiro.cache;

import io.github.ushiro.data.DataModel;

public interface Cache<KeyType, ValueType> {
    public enum Strategy {
        LRU,
        MRU
    }
    public void put(KeyType key, ValueType valueType);
    public ValueType get(KeyType key);
    public boolean contains(KeyType key);
    public void invalidate(KeyType key);
}