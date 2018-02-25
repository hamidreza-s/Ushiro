package io.github.ushiro.utils;

import io.github.ushiro.Config;

/**
 * A utility class for validating the long URL string based
 * on minimum length, maximum length, and regex pattern which
 * are specified in the config file.
 */
public class UrlValidator {

    /**
     * Check if the given URL is valid based on the configuration
     *
     * @param url The given URL to be checked
     * @return The URL validity
     */
    public static boolean isUrlValid(String url) {
        if(url == null)
            return false;

        if(url.length() < Config.getLongUrlValidLengthMin())
            return false;

        if(url.length() > Config.getLongUrlValidLengthMax())
            return false;

        return url.matches(Config.getLongUrlValidRegex());
    }
}
