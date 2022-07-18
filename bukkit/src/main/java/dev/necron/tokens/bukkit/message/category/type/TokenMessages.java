package dev.necron.tokens.bukkit.message.category.type;

import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.MessageType;
import dev.necron.tokens.bukkit.message.category.MessageCategory;
import dev.necron.tokens.common.config.key.ConfigKeys;

public final class TokenMessages extends MessageCategory {

    public TokenMessages() {
        super(
                "TokenMessages",
                MessageType.of(ConfigKeys.Language.TOKEN_MESSAGES_TYPE.getValue())
        );
    }

    public final Message TOKEN_RECEIVED = Message.of(
            ConfigKeys.Language.TOKEN_RECEIVED_MESSAGE.getValue(),
            this
    );

    public final Message TOKEN_SENT = Message.of(
            ConfigKeys.Language.TOKEN_SENT_MESSAGE.getValue(),
            this
    );

    public final Message TOKEN_SHOW = Message.of(
            ConfigKeys.Language.TOKEN_SHOW_MESSAGE.getValue(),
            this
    );

    public final Message NOT_ENOUGH_TOKENS = Message.of(
            ConfigKeys.Language.TOKEN_NOT_ENOUGH_TOKENS_MESSAGE.getValue(),
            this
    );

    public final Message CANNOT_SEND_TO_SELF = Message.of(
            ConfigKeys.Language.CANNOT_SEND_TO_YOURSELF_MESSAGE.getValue(),
            this
    );

    public final Message AMOUNT_NOT_VALID = Message.of(
            ConfigKeys.Language.AMOUNT_NOT_VALID_MESSAGE.getValue(),
            this
    );

}
