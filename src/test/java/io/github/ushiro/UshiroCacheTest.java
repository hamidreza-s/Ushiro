package io.github.ushiro;

import io.github.ushiro.cache.Cache;
import io.github.ushiro.cache.CacheFactory;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UshiroCacheTest {

    @Test
    public void testMRUCache() throws Exception {
        int cacheSize = 64;
        Cache.Strategy cacheStrategy = Cache.Strategy.MRU;
        CacheFactory<Integer, Integer> cacheFactory = new CacheFactory<Integer, Integer>();
        Cache<Integer, Integer> cache = cacheFactory.newInstance(cacheSize, cacheStrategy);

        for(int i = 0; i < cacheSize; i++)
            cache.put(i, i);

        for(int i = 0; i < cacheSize; i++) {
            assertTrue(cache.contains(i));
            assertTrue(cache.get(i) == i);
        }
    }

    @Test
    public void testLRUCache() throws Exception {
        int cacheSize = 64;
        Cache.Strategy cacheStrategy = Cache.Strategy.LRU;
        CacheFactory<Integer, Integer> cacheFactory = new CacheFactory<Integer, Integer>();
        Cache<Integer, Integer> cache = cacheFactory.newInstance(cacheSize, cacheStrategy);

        for(int i = 0; i < cacheSize; i++)
            cache.put(i, i);

        for(int i = 0; i < cacheSize; i++) {
            assertTrue(cache.contains(i));
            assertTrue(cache.get(i) == i);
        }
    }
}
