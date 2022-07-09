package dev.necron.tokens.bukkit.message;

import com.hakan.core.HCore;
import org.bukkit.entity.Player;

public enum MessageType {
    MESSAGE, ACTIONBAR, TITLE;

    public void send(Player player, String message) {
        switch (this) {
            case MESSAGE:
                player.sendMessage(message);
                break;
            case ACTIONBAR:
                HCore.sendActionBar(player, message);
                break;
            case TITLE:
                String[] split = message.split("\n");
                if (split.length != 2) {
                    HCore.sendTitle(player, message, "", 40, 10, 10);
                }else {
                    HCore.sendTitle(player, split[0], split[1], 40, 10, 10);
                }
                break;
        }
    }

    public static MessageType of(String name) {
        for (MessageType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("MessageType not found: " + name);
    }
}
