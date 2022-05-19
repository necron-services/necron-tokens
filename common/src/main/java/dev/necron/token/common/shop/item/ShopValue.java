package dev.necron.token.common.shop.item;

public interface ShopValue {

    /**
     * execute the shop item to the player
     *
     * @param player the player to execute the item to
     * @param <T>    the type of player
     */
    <T> void execute(T player);

}
