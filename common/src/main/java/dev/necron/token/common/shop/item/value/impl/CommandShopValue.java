package dev.necron.token.common.shop.item.value.impl;

import dev.necron.token.common.shop.item.value.ShopValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class CommandShopValue implements ShopValue {

    /**
     * The command to execute
     */
    protected final String command;

}
