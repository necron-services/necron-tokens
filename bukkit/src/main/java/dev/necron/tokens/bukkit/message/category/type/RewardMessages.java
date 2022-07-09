package dev.necron.tokens.bukkit.message.category.type;

import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.MessageType;
import dev.necron.tokens.bukkit.message.category.MessageCategory;
import dev.necron.tokens.common.config.key.ConfigKeys;

public class RewardMessages extends MessageCategory {

    public RewardMessages() {
        super(
                "RewardMessages",
                MessageType.of(ConfigKeys.Language.REWARD_MESSAGES_TYPE.getValue())
        );
    }

    public final Message EARNED_TOKENS_FROM_BLOCK = Message.of(
            ConfigKeys.Language.EARNED_TOKENS_FROM_BLOCK_MESSAGE.getValue(),
            this
    );

    public final Message EARNED_TOKENS_FROM_ENTITY = Message.of(
            ConfigKeys.Language.EARNED_TOKENS_FROM_ENTITY_MESSAGE.getValue(),
            this
    );

    public final Message EARNED_TOKENS_LAST_TIME = Message.of(
            ConfigKeys.Language.EARNED_TOKENS_LAST_TIME_MESSAGE.getValue(),
            this
    );

}
