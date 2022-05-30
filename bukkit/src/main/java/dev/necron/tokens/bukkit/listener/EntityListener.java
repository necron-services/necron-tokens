package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.common.drop.TokenDropType;
import dev.necron.tokens.common.drop.handler.TokenDropHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;
        TokenDropHandler.execute(TokenDropType.ENTITY, player.getUniqueId(), event.getEntity());
    }

}
