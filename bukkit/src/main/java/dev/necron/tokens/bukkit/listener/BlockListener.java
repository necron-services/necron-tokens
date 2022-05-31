package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.bukkit.event.TokenDropEvent;
import dev.necron.tokens.common.drop.TokenDrop;
import dev.necron.tokens.common.drop.TokenDropType;
import dev.necron.tokens.common.drop.handler.TokenDropHandler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginManager;

public class BlockListener implements Listener {

    private static final TokenDropType TOKEN_DROP_TYPE = TokenDropType.BLOCK;

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        TokenDropHandler.find(TOKEN_DROP_TYPE).ifPresent(drops -> {
            for (TokenDrop drop : drops) {
                long amount = drop.randomDrop();
                TokenDropEvent dropEvent = new TokenDropEvent(player, TOKEN_DROP_TYPE, amount, false);
                pluginManager.callEvent(dropEvent);
                if (!dropEvent.isCancelled()) {
                    drop.execute(player.getUniqueId(), block, dropEvent.getAmount());
                }
            }
        });
    }

}
