package io.github.ushiro.utils;

import io.github.ushiro.Config;

import java.util.Random;

public class RandomGenerator {

    private static Random random;

    public static Random getInstance() {
        if(random != null)
            return random;

        random = new Random();
        return random;
    }

    public static String generate() {
        int length = Config.getShortUrlLength();
        String chars = Config.getShortUrlChars();
        String result = "";
        for (int i = 0; i < length; i++)
            result += chars.charAt(getInstance().nextInt(chars.length()));

        // @todo: check if result does not exist

        return result;
    }

}
