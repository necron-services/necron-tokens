package dev.necron.tokens.common.drop;

import java.util.UUID;

public interface TokenDrop {

    /**
     * Executes the drop.
     * @param playerUUID The player who dropped the token.
     * @param <T> The type of dropper.
     */
    <T> void execute(UUID playerUUID, T dropper);

    /**
     * Executes the drop.
     * If you want to execute without chance use this.
     *
     * @param playerUUID The player who dropped the token.
     * @param <T> The type of dropper.
     * @param amount The amount of tokens to drop.
     */
    <T> void execute(UUID playerUUID, T dropper, long amount);

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
     * Randomly get a drop with min and max.
     * @return The drop.
     */
    long randomDrop();

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
