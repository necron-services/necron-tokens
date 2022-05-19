package dev.necron.token.common.shop;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {

    private final Map<String, Shop> shopMap = new HashMap<>();

    /**
     * Gets the shop.
     *
     * @param name the name
     * @return the shop
     */
    @Nullable
    public Shop getShop(String name) {
        return shopMap.get(name);
    }

    /**
     * Adds the shop.
     *
     * @param shop the shop
     */
    public void addShop(Shop shop) {
        shopMap.put(shop.getName(), shop);
    }

    /**
     * Removes the shop.
     *
     * @param name the name
     */
    public void removeShop(String name) {
        shopMap.remove(name);
    }

    /**
     * Checks if a Shop is in the map.
     *
     * @param name the name
     * @return true if the shop is in the map
     */
    public boolean containsShop(String name) {
        return shopMap.containsKey(name);
    }

}
