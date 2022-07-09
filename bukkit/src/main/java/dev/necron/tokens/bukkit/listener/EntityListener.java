package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.bukkit.blocker.Blockers;
import dev.necron.tokens.bukkit.event.TokenDropEvent;
import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.category.MessageCategories;
import dev.necron.tokens.bukkit.message.category.type.RewardMessages;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.drop.TokenDrop;
import dev.necron.tokens.common.drop.TokenDropType;
import dev.necron.tokens.common.drop.TokenDropManager;
import dev.necron.tokens.common.util.ChanceUtil;
import dev.necron.tokens.common.util.formatter.TokenFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.PluginManager;

public class EntityListener implements Listener {

    private static final TokenDropType TOKEN_DROP_TYPE = TokenDropType.ENTITY;

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;

        String worldName = player.getWorld().getName();
        if (Blockers.WorldBlocker.getInstance().control(worldName)) return;

        Entity entity = event.getEntity();
        TokenDropManager.find(TOKEN_DROP_TYPE).ifPresent(drops -> {
            for (TokenDrop drop : drops) {
                if (!drop.isSimilar(entity)) continue;
                if (!ChanceUtil.tryChance(drop.getChance())) continue;

                long amount = drop.randomDrop();
                TokenDropEvent dropEvent = new TokenDropEvent(player, TOKEN_DROP_TYPE, amount, false);
                pluginManager.callEvent(dropEvent);
                if (!dropEvent.isCancelled()) {
                    drop.execute(player.getUniqueId(), entity, dropEvent.getAmount());

                    RewardMessages messages = (RewardMessages) MessageCategories.REWARD_MESSAGES.getInstance();
                    Message message = messages.EARNED_TOKENS_FROM_ENTITY;
                    message.execute(player, () -> LanguageUtil.replace(message.getValue(),
                            new String[]{"%amount%", "%entity%", "%chance%"},
                            new String[]{TokenFormatter.format(dropEvent.getAmount()), entity.getType().name(), String.valueOf(drop.getChance())}
                    ));
                }
            }
        });
    }

}
