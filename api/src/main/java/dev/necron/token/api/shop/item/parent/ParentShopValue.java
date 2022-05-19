package dev.necron.token.api.shop.item.parent;

import dev.necron.token.api.shop.item.ShopValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ParentShopValue {

    private final String name;
    private final double price;
    private final ShopValue[] shopValues;

    private int stock;

}
