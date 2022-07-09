package dev.necron.tokens.common.shop;

import dev.necron.tokens.common.shop.item.ShopItem;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class Shop {

    private final Map<String, ShopItem> shopItemMap = new HashMap<>();

    private Runnable refreshRunnable;

    private long refreshTimeLeft;

    @Getter private final String name;
    @Getter private final boolean refreshable;
    private final long refreshTime;

    public Shop(String name, long refreshTime) {
        this.name = name;
        this.refreshable = refreshTime > 0;
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
        return isRefreshable()
                ? (refreshTimeLeft - System.currentTimeMillis()) / 1000
                : 0;
    }

    public void onRefresh(Runnable runnable) {
        this.refreshRunnable = runnable;
    }

    public void refresh() {
        refreshTimeLeft = System.currentTimeMillis() + (refreshTime * 1000);
        shopItemMap.values().forEach(shopItem -> shopItem.setStock(shopItem.getMaxStock()));
        refreshRunnable.run();
    }

}
