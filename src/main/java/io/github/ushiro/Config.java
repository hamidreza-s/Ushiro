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

    public static Properties getConfig() {
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
        return getConfig().getProperty(key);
    }

    public static String getHttpPort() {
        return getProperty("http_port");
    }

    public static String getHttpHost() {
        return getProperty("http_host");
    }

    public static String getHttpIdleTimeout() {
        return getProperty("http_idle_timeout");
    }

}