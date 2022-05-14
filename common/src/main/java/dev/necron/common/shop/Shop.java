package dev.necron.common.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class Shop {

    private final String name;
    private final long shopRefreshTime;
    private final ShopItem<?>[] shopItems;

    private long currentShopRefreshTime;

}
