package dev.necron.tokens.bukkit.message.category;

import dev.necron.tokens.bukkit.message.category.type.RewardMessages;
import dev.necron.tokens.bukkit.message.category.type.ShopMessages;
import dev.necron.tokens.bukkit.message.category.type.TokenMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageCategories {
    TOKEN_MESSAGES(TokenMessages.class),
    REWARD_MESSAGES(RewardMessages.class),
    SHOP_MESSAGES(ShopMessages.class);

    private MessageCategory instance;
    private final Class<? extends MessageCategory> clazz;

    public static void reload() {
        try {
            for (MessageCategories category : values()) {
                category.instance = category.clazz
                        .getConstructor()
                        .newInstance();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
