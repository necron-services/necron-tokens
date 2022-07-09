package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.common.storage.Storage;
import dev.necron.tokens.common.storage.StorageProvider;
import dev.necron.tokens.common.player.TokenPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final Storage storage = StorageProvider.getStorage();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        storage.loadPlayer(event.getPlayer().getUniqueId()).ifPresent(TokenPlayerManager::put);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        TokenPlayerManager.find(event.getPlayer().getUniqueId()).ifPresent(storage::savePlayer);
    }

}
