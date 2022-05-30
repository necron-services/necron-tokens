package dev.necron.tokens.common.runnable;

import dev.necron.tokens.common.shop.handler.ShopHandler;

public class ShopRefreshRunnable implements Runnable {

    @Override
    public void run() {

        ShopHandler.getShops().forEach(shop -> {
            if (shop.getRefreshTimeLeft() <= 0) {
                shop.refresh();
            }
        });

    }

}
