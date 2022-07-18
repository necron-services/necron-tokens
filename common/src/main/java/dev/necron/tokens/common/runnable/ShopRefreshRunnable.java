package dev.necron.tokens.common.runnable;

import dev.necron.tokens.common.shop.ShopManager;

public class ShopRefreshRunnable implements Runnable {

    @Override
    public void run() {
        ShopManager.findAll().forEach(shop -> {
            if (shop.isRefreshable() && shop.getRefreshTimeLeft() <= 0) {
                shop.refresh();
            }
        });
    }

}
