package dev.necron.tokens.bukkit.shop.item.value;

import org.jetbrains.annotations.Nullable;

public enum BukkitShopValueType {
    COMMAND, ITEM;

    @Nullable
    public static BukkitShopValueType of(String value) {
        for (BukkitShopValueType type : values()) {
            if (type.name().equalsIgnoreCase(value)) return type;
        }
        return null;
    }

}
