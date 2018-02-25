package io.github.ushiro.cache;

/**
 * A Least-Recently-Used implementation of cache interface
 *
 * @param <KeyType> The generic type of cache table key
 * @param <ValueType> The generic type of cache table value
  */
public class LRUCache<KeyType, ValueType> extends AbstractCache<KeyType, ValueType> {

    /**
     * Object constructor which passes the argument to its parent
     */
    public LRUCache(int size) {
        super(size);
    }

    /**
     * Put a key-value record into cache table
     */
    public void put(KeyType key, ValueType value) {
        if(list.size() == this.size) {
            this.prune();
        }
        list.addFirst(key);
        map.put(key, value);
    }

    /**
     * Get value by key from cache table
     */
    public ValueType get(KeyType key) {
        if(list.remove(key)) {
            list.addFirst(key);
            return map.get(key);
        }
        return null;
    }
}
