package dev.necron.token.common.shop.item.value.impl;

import dev.necron.token.common.shop.item.value.ShopValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class ItemShopValue implements ShopValue {

    /**
     * The value of the item to execute.
     */
    protected final Object value;

}
