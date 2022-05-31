package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.bukkit.event.TokenDropEvent;
import dev.necron.tokens.common.drop.TokenDrop;
import dev.necron.tokens.common.drop.TokenDropType;
import dev.necron.tokens.common.drop.handler.TokenDropHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.PluginManager;

public class EntityListener implements Listener {

    private static final TokenDropType TOKEN_DROP_TYPE = TokenDropType.BLOCK;

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;
        Entity entity = event.getEntity();
        TokenDropHandler.find(TOKEN_DROP_TYPE).ifPresent(drops -> {
            for (TokenDrop drop : drops) {
                long amount = drop.randomDrop();
                TokenDropEvent dropEvent = new TokenDropEvent(player, TOKEN_DROP_TYPE, amount, false);
                pluginManager.callEvent(dropEvent);
                if (!dropEvent.isCancelled()) {
                    drop.execute(player.getUniqueId(), entity, dropEvent.getAmount());
                }
            }
        });
    }

}
