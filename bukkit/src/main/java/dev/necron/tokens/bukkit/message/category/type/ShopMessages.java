package dev.necron.tokens.bukkit.message.category.type;

import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.MessageType;
import dev.necron.tokens.bukkit.message.category.MessageCategory;
import dev.necron.tokens.common.config.key.ConfigKeys;

public final class ShopMessages extends MessageCategory {

    public ShopMessages() {
        super(
                "ShopMessages",
                MessageType.of(ConfigKeys.Language.SHOP_MESSAGES_TYPE.getValue())
        );
    }

    public final Message INVENTORY_FULL = Message.of(
            ConfigKeys.Language.INVENTORY_FULL_MESSAGE.getValue(),
            this
    );

    public final Message NOT_ENOUGH_THINGS = Message.of(
            ConfigKeys.Language.NOT_ENOUGH_THINGS_MESSAGE.getValue(),
            this
    );

    public final Message BOUGHT_SHOP_ITEM = Message.of(
            ConfigKeys.Language.BOUGHT_SHOP_ITEM_MESSAGE.getValue(),
            this
    );

    public final Message SHOPS_HAS_BEEN_REFRESHED = Message.of(
            ConfigKeys.Language.SHOPS_HAS_BEEN_REFRESHED_MESSAGE.getValue(),
            this
    );

}
