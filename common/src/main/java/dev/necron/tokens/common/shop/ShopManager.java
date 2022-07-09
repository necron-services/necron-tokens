package dev.necron.tokens.common.shop;

import dev.necron.tokens.common.shop.loader.ShopLoader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShopManager {

    private static final Map<String, Shop> shopMap = new HashMap<>();

    /**
     * Initialize the shop handler
     * @param shopLoader The shop loader
     */
    public static void init(ShopLoader shopLoader) {
        shopMap.clear();
        shopLoader.load().forEach(shop -> shopMap.put(shop.getName(), shop));
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

    /**
     * Gets the shops.
     * @return The shops
     */
    public static Collection<Shop> findAll() {
        return shopMap.values();
    }

}
