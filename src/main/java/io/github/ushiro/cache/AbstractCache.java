package io.github.ushiro.cache;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * The abstract class of cache which defines common fields
 * and methods. It also specifies the methods which must
 * be implemented by child classes.
 *
 * @param <KeyType> The generic type of cache table key
 * @param <ValueType> The generic type of cache table value
 */
public abstract class AbstractCache<KeyType, ValueType> implements Cache<KeyType, ValueType> {

    protected int size;
    protected HashMap<KeyType, ValueType> map;
    protected LinkedList<KeyType> list;

    /**
     * Object constructor which gets the cache size
     *
     * @param size The size of cache table
     */
    public AbstractCache(int size) {
        this.size = size;
        map = new HashMap<KeyType, ValueType>(size);
        list = new LinkedList<KeyType>();
    }

    public abstract void put(KeyType key, ValueType value);

    public abstract ValueType get(KeyType key);

    /**
     * Check if the cache table contains the given key
     */
    public boolean contains(KeyType key) {
        return map.containsKey(key);
    }

    /**
     * Remove the given record from the cache data structure
     */
    public void invalidate(KeyType key) {
        list.remove(key);
        map.remove(key);
    }

    /**
     * Remove the last record from the cache data structure
     */
    public void prune() {
        KeyType key = list.removeLast();
        map.remove(key);
    }
}
