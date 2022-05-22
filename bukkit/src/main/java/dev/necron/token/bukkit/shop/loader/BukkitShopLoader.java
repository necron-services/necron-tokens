package dev.necron.token.bukkit.shop.loader;

import dev.necron.token.common.shop.Shop;
import dev.necron.token.common.shop.loader.ShopLoader;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public class BukkitShopLoader implements ShopLoader {

    @Override
    public Collection<Shop> load() throws Exception {
        return CompletableFuture.supplyAsync(() -> {

            Collection<Shop> shops = new HashSet<>();

            return shops;

        }).get();
    }

}
