package io.github.ushiro.utils;

import io.github.ushiro.Config;

public class UrlValidator {

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
