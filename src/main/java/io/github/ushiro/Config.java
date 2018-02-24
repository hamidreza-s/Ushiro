package io.github.ushiro;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config
{
    private static Properties config;

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

    public static String getProperty(String key) {
        return getInstance().getProperty(key);
    }

    public static String getHttpPort() {
        return getProperty("http.port");
    }

    public static String getHttpHost() {
        return getProperty("http.host");
    }

    public static String getHttpIdleTimeout() {
        return getProperty("http.idle.timeout");
    }

    public static String getLongUrlValidRegex() {
        return getProperty("long.url.valid.regex");
    }

    public static int getLongUrlValidLengthMin() {
        return Integer.parseInt(getProperty("long.url.valid.length.min"));
    }

    public static int getLongUrlValidLengthMax() {
        return Integer.parseInt(getProperty("long.url.valid.length.max"));
    }

    public static String getShortUrlChars() {
        return getProperty("short.url.chars");
    }

    public static int getShortUrlLength() {
        return Integer.parseInt(getProperty("short.url.length"));
    }

}