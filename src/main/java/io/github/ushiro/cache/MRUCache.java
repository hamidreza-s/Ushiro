package io.github.ushiro.cache;

/**
 * A Most-Recently-Used implementation of cache interface
 *
 * @param <KeyType> The generic type of cache table key
 * @param <ValueType> The generic type of cache table value
 */
public class MRUCache<KeyType, ValueType> extends AbstractCache<KeyType, ValueType> {

    /**
     * Object constructor which passes the argument to its parent
     */
    public MRUCache(int size) {
        super(size);
    }

    /**
     * Put a key-value record into cache table
     */
    @Override
    public void put(KeyType key, ValueType value) {
        if (list.size() == this.size) {
            this.prune();
        }
        list.addLast(key);
        map.put(key, value);
    }

    /**
     * Get value by key from cache table
     */
    @Override
    public ValueType get(KeyType key) {
        if(list.remove(key)) {
            list.addLast(key);
            return map.get(key);
        }
        return null;
    }
}
