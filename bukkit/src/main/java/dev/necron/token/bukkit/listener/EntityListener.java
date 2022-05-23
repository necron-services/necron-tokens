package dev.necron.token.bukkit.listener;

import dev.necron.token.common.drop.TokenDropType;
import dev.necron.token.common.drop.handler.TokenDropHandler;
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
        TokenDropHandler.execute(TokenDropType.ENTITY, player, event.getEntity());
    }

}
