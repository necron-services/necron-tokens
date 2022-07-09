package dev.necron.tokens.bukkit.message.category;

import dev.necron.tokens.bukkit.message.MessageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
public abstract class MessageCategory {

    private final String name;
    private final MessageType messageType;

    public void execute(Player player, String message) {
        messageType.send(player, message);
    }

}
