package dev.necron.tokens.common.shop.loader;

import dev.necron.tokens.common.shop.Shop;

import java.util.Collection;

public interface ShopLoader {

    /**
     * Loads the shops
     * @return the shops
     */
    Collection<Shop> load();

}
