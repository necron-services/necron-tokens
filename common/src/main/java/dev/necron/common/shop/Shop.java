package dev.necron.common.shop;

import dev.necron.common.shop.item.parent.ParentShopValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class Shop {

    private final String name;
    private final long shopRefreshTime;
    private final ParentShopValue[] shopItems;

    private long currentShopRefreshTime;

}
