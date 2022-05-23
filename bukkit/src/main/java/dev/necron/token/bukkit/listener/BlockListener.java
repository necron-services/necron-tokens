package dev.necron.token.bukkit.listener;

import dev.necron.token.common.drop.TokenDropType;
import dev.necron.token.common.drop.handler.TokenDropHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        TokenDropHandler.execute(TokenDropType.BLOCK, player, event.getBlock());
    }

}
