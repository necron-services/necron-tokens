package dev.necron.tokens.common.util;

public class ChanceUtil {

    /**
     * Returns true if the chance is greater than the given chance.
     * @param chance the chance
     * @return true if the chance is greater than the given chance
     */
    public static boolean tryChance(double chance) {
        double random = Math.random() * 100;
        return (random - chance) < 0;
    }

}
