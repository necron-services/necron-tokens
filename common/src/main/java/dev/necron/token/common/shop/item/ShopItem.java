package dev.necron.token.common.shop.item;

import dev.necron.token.common.shop.item.value.ShopValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ShopItem {

    private final String name;
    private final double price;
    private final ShopValue[] shopValues;

    private int stock;

}
