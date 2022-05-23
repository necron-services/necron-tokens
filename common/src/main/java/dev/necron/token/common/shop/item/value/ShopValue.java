package dev.necron.token.common.shop.item.value;

import java.util.UUID;

public interface ShopValue {

    /**
     * execute the shop item to the player
     *
     * @param playerUUID the player uuid to execute the shop item
     */
    void execute(UUID playerUUID);

}
