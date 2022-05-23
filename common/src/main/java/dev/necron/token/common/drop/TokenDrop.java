package dev.necron.token.common.drop;

import java.util.UUID;

public interface TokenDrop {

    /**
     * Executes the drop.
     * @param playerUUID The player who dropped the token.
     * @param <T> The type of player.
     */
    <T, V> void execute(UUID playerUUID, V dropper);

    /**
     * Same as equals, but with a generic type.
     * @param dropper The dropper.
     * @return True if the dropper is the same.
     */
    boolean isSimilar(Object dropper);

    /**
     * Gets the drop causing.
     *
     * @return the drop causing
     */
    <T> T getDropper();

    /**
     * @return the min amount of tokens dropped
     */
    long getMinDrop();

    /**
     * @return the max amount of tokens dropped
     */
    long getMaxDrop();

    /**
     * @return the chance of dropping
     */
    double getChance();

}
