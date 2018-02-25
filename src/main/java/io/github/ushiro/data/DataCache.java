package io.github.ushiro.data;

import io.github.ushiro.Config;
import io.github.ushiro.cache.Cache;
import io.github.ushiro.cache.CacheFactory;

public class DataCache {

    private static Cache<String, DataModel> instance;

    public static Cache<String, DataModel> getInstance() {
        if (instance != null)
            return instance;

        int cacheSize = Config.getDataCacheSize();
        String cacheStrategy = Config.getDataCacheEvictionStrategy();
        CacheFactory<String, DataModel> cacheFactory = new CacheFactory<String, DataModel>();
        instance = cacheFactory.newInstance(cacheSize, Cache.Strategy.valueOf(cacheStrategy));
        return instance;
    }
}
