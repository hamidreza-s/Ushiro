package io.github.ushiro;

import java.io.InputStream;
import java.util.Properties;

/**
 * The configuration class containing a singleton instance of
 * properties which is loaded from "config.peroperties" file
 * in resource directory. This class is responsible for casting
 * the configuration values from string to the required types.
 */
public class Config {

    private static Properties config;

    /**
     * Get the singletone instance of the configuration object
     *
     * @return The properties object
     */
    public static Properties getInstance() {
        if(config != null) {
            return config;
        } else {
            config = new java.util.Properties();
            String resourceName = "io/github/ushiro/ushiro.properties";

            try(InputStream fileStream = Config.class.getClassLoader().getResourceAsStream(resourceName)) {
                config.load(fileStream);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return config;
        }
    }

    /**
     * Get the value of config file
     *
     * @param key The string key of the config property
     * @return The string property loaded from file
     */
    public static String getProperty(String key) {
        return getInstance().getProperty(key);
    }

    /**
     * Get the listening HTTP port
     */
    public static String getHttpPort() {
        return getProperty("http.port");
    }

    /**
     * Get the listening HTTP host
     */
    public static String getHttpHost() {
        return getProperty("http.host");
    }

    /**
     * Get the idle timeout of the HTTP listener
     */
    public static String getHttpIdleTimeout() {
        return getProperty("http.idle.timeout");
    }

    /**
     * Get the regex pattern of the accepted long URLs
     */
    public static String getLongUrlValidRegex() {
        return getProperty("long.url.valid.regex");
    }

    /**
     * Get the minimum length of the accepted long URLs
     */
    public static int getLongUrlValidLengthMin() {
        return Integer.parseInt(getProperty("long.url.valid.length.min"));
    }

    /**
     * Get the maximum length of the accepted long URLs
     */
    public static int getLongUrlValidLengthMax() {
        return Integer.parseInt(getProperty("long.url.valid.length.max"));
    }

    /**
     * Get the accepted character list of the short URLs
     */
    public static String getShortUrlChars() {
        return getProperty("short.url.chars");
    }

    /**
     * Get the accepted length of the short URLs
     */
    public static int getShortUrlLength() {
        return Integer.parseInt(getProperty("short.url.length"));
    }

    /**
     * Check if data must be persisted in a database
     */
    public static boolean isDataPersistent() {
        return Boolean.parseBoolean(getProperty("data.persistent"));
    }

    /**
     * Get a comma-separated list of database nodes to persist data
     */
    public static String getDataPersistentNodes() {
        return getProperty("data.persistent.nodes");
    }

    /**
     * Get the specified size of data cache
     */
    public static int getDataCacheSize() {
        return Integer.parseInt(getProperty("data.cache.size"));
    }

    /**
     * Get the specified eviction strategy of the data cache
     */
    public static String getDataCacheEvictionStrategy() {
        return getProperty("data.cache.eviction.strategy");
    }
}
