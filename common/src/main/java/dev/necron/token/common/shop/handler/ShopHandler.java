package dev.necron.token.common.shop.handler;

import dev.necron.token.common.shop.Shop;
import dev.necron.token.common.shop.loader.ShopLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShopHandler {

    private static final Map<String, Shop> shopMap = new HashMap<>();

    public static void init(ShopLoader shopLoader) {
        try {
            shopLoader.load().forEach(shop -> shopMap.put(shop.getName(), shop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the shop.
     *
     * @param name the name
     * @return the shop
     */
    public static Optional<Shop> find(String name) {
        return Optional.ofNullable(shopMap.get(name));
    }

    /**
     * Adds the shop.
     *
     * @param shop the shop
     */
    public static void put(Shop shop) {
        shopMap.put(shop.getName(), shop);
    }

    /**
     * Removes the shop.
     *
     * @param name the name
     */
    public static void remove(String name) {
        shopMap.remove(name);
    }

}
