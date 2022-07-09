package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.bukkit.event.TokenDropEvent;
import dev.necron.tokens.bukkit.util.sound.XSound;
import dev.necron.tokens.common.config.key.ConfigKey;
import dev.necron.tokens.common.config.key.ConfigKeys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TokenDropListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(TokenDropEvent event) {

        boolean soundsEnabled = ConfigKeys.Settings.EARN_TOKENS_SOUND_ENABLED.getValue();
        if (!soundsEnabled) return;

        String sound = null;
        switch (event.getCause()) {
            case BLOCK:
                sound = ConfigKeys.Settings.EARN_TOKENS_FROM_BLOCK_SOUND.getValue();
                break;
            case ENTITY:
                sound = ConfigKeys.Settings.EARN_TOKENS_FROM_ENTITY_SOUND.getValue();
                break;
        }
        XSound.matchXSound(sound).ifPresent(xSound -> {
            xSound.play(event.getPlayer());
        });
    }

}
