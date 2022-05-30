package dev.necron.tokens.common.shop;

import dev.necron.tokens.common.shop.item.ShopItem;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Shop {

    private final Map<String, ShopItem> shopItemMap = new HashMap<>();

    private long refreshTimeLeft;

    @Getter private final String name;
    private final long refreshTime;

    public Shop(String name, long refreshTime) {
        this.name = name;
        this.refreshTime = refreshTime;
        this.refreshTimeLeft = System.currentTimeMillis() + (refreshTime * 1000);
    }

    public Optional<ShopItem> find(String shopItem) {
        return Optional.ofNullable(shopItemMap.get(shopItem));
    }

    public void put(ShopItem shopItem) {
        shopItemMap.put(shopItem.getName(), shopItem);
    }

    public void remove(String name) {
        shopItemMap.remove(name);
    }

    public long getRefreshTimeLeft() {
        return (refreshTimeLeft - System.currentTimeMillis()) / 1000;
    }

    public void refresh() {
        refreshTimeLeft = System.currentTimeMillis() + (refreshTime * 1000);
        shopItemMap.values().forEach(shopItem -> shopItem.setStock(shopItem.getMaxStock()));
    }

}
