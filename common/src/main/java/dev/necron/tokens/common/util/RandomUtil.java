package dev.necron.tokens.common.util;

public class RandomUtil {

    /**
     * Returns a random long between min and max, inclusive.
     * @param min the minimum
     * @param max the maximum
     * @return a random long between min and max, inclusive
     */
    public static long random(long min, long max) {
        return (long) (Math.random() * (max - min + 1)) + min;
    }

}
