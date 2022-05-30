package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.common.drop.TokenDropType;
import dev.necron.tokens.common.drop.handler.TokenDropHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        TokenDropHandler.execute(TokenDropType.BLOCK, player.getUniqueId(), event.getBlock());
    }

}
