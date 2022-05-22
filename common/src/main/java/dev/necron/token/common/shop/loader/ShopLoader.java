package dev.necron.token.common.shop.loader;

import dev.necron.token.common.shop.Shop;

import java.util.Collection;

public interface ShopLoader {

    /**
     * Loads the shops method
     * @return the shops
     */
    Collection<Shop> load() throws Exception;

}
