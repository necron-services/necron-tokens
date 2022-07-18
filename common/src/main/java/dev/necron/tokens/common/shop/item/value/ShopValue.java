package dev.necron.tokens.common.shop.item.value;

import dev.necron.tokens.common.shop.item.ShopItem;

import java.util.UUID;

public interface ShopValue {

    /**
     * execute the shop item to the player
     *
     * @param playerUUID the player uuid to execute the shop item
     */
    void execute(ShopItem shopItem, UUID playerUUID);

}
