package io.github.ushiro.cache;

/**
 * The cache interface to have a consistent API for
 * different types of cache implementation.
 *
 * @param <KeyType> The generic type of cache table key
 * @param <ValueType> The generic type of cache table value
 */
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