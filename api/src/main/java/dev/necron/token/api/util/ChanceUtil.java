package dev.necron.token.api.util;

public class ChanceUtil {

    public static boolean tryChance(double chance) {
        double random = Math.random() * 100;
        return (random - chance) < 0;
    }

}
