package io.github.ushiro.cache;

public class LRUCache<KeyType, ValueType> extends AbstractCache<KeyType, ValueType> {
    public LRUCache(int size) {
        super(size);
    }

    public void put(KeyType key, ValueType value) {
        if (list.size() == this.size) {
            this.prune();
        }
        list.addFirst(key);
        map.put(key, value);
    }

    public ValueType get(KeyType key) {
        boolean res = list.remove(key);
        if (res) {
            list.addFirst(key);
            return map.get(key);
        }
        return null;
    }
}