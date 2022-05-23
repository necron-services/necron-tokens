package dev.necron.token.bukkit.listener;

import dev.necron.token.common.storage.Storage;
import dev.necron.token.common.storage.StorageProvider;
import dev.necron.token.common.token.TokenPlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final Storage storage = StorageProvider.getStorage();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        storage.loadPlayer(event.getPlayer().getUniqueId()).ifPresent(TokenPlayerHandler::put);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        TokenPlayerHandler.find(event.getPlayer().getUniqueId()).ifPresent(storage::savePlayer);
    }

}
