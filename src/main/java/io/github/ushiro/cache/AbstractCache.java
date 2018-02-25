package io.github.ushiro.cache;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class AbstractCache<KeyType, ValueType> implements Cache<KeyType, ValueType> {
    protected int size;
    protected HashMap<KeyType, ValueType> map;
    protected LinkedList<KeyType> list;

    public AbstractCache(int size) {
        this.size = size;
        map = new HashMap<KeyType, ValueType>(size);
        list = new LinkedList<KeyType>();
    }

    public abstract void put(KeyType key, ValueType value);

    public abstract ValueType get(KeyType key);

    public boolean contains(KeyType key) {
        return map.containsKey(key);
    }

    public void invalidate(KeyType key) {
        list.remove(key);
        map.remove(key);
    }

    public void prune() {
        KeyType key = list.removeLast();
        map.remove(key);
    }

}