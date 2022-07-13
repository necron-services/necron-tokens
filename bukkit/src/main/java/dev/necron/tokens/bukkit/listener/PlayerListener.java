package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.common.storage.Storage;
import dev.necron.tokens.common.storage.StorageProvider;
import dev.necron.tokens.common.player.TokenPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerListener implements Listener {

    private final Storage storage = StorageProvider.getStorage();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLoginAsync(AsyncPlayerPreLoginEvent event) {
        storage.loadPlayer(event.getUniqueId()).ifPresent(TokenPlayerManager::put);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (TokenPlayerManager.find(uuid).isPresent()) return;
        storage.loadPlayer(uuid).ifPresent(TokenPlayerManager::put);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        TokenPlayerManager.find(event.getPlayer().getUniqueId()).ifPresent(storage::savePlayer);
    }

}
