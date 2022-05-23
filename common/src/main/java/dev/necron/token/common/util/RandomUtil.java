package dev.necron.token.common.util;

public class RandomUtil {

    public static long random(long min, long max) {
        return (long) (Math.random() * (max - min + 1)) + min;
    }

}
