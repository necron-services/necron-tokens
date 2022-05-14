package dev.necron.common.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class ShopItem<V> {

    private final String name;
    private final V item;
    private final long price;

    private long stock;

}
