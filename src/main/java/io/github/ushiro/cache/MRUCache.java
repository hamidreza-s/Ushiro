package io.github.ushiro.cache;

public class MRUCache<KeyType, ValueType> extends AbstractCache<KeyType, ValueType> {

    public MRUCache(int size) {
        super(size);
    }

    @Override
    public void put(KeyType key, ValueType value) {
        if (list.size() == this.size) {
            this.prune();
        }
        list.addLast(key);
        map.put(key, value);
    }

    @Override
    public ValueType get(KeyType key) {
        if (list.remove(key)) {
            list.addLast(key);
            return map.get(key);
        }
        return null;
    }
}