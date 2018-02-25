package io.github.ushiro.utils;

import io.github.ushiro.Config;
import java.util.Random;

/**
 * This is a singleton class responsible for generating
 * a random string based on the length and character limit
 * of short URL which is specified in the config file.
 */
public class RandomGenerator {

    private static Random random;

    /**
     * Get the singleton instance of the random object
     *
     * @return Random object to be used as seed
     */
    public static Random getInstance() {
        if(random != null)
            return random;

        random = new Random();
        return random;
    }

    /**
     * Generate a random string based on config limits
     *
     * @return A random string
     */
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
