package dev.necron.tokens.bukkit;

import com.google.common.collect.ImmutableList;
import com.hakan.core.HCore;
import com.hakan.core.command.HCommandHandler;
import dev.necron.tokens.bukkit.blocker.Blockers;
import dev.necron.tokens.bukkit.command.TokenShopCommand;
import dev.necron.tokens.bukkit.command.admin.TokenAdminCommand;
import dev.necron.tokens.bukkit.drop.loader.BukkitTokenDropLoader;
import dev.necron.tokens.bukkit.hook.PlaceholderHook;
import dev.necron.tokens.bukkit.leaderboard.leader.finder.BukkitLeaderFinder;
import dev.necron.tokens.bukkit.listener.*;
import dev.necron.tokens.bukkit.menu.MenuManager;
import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.category.MessageCategories;
import dev.necron.tokens.bukkit.message.category.type.RewardMessages;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.runnable.EarnedTokensMessageRunnable;
import dev.necron.tokens.bukkit.runnable.MenuRefreshRunnable;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.drop.TokenDropManager;
import dev.necron.tokens.common.leaderboard.Leaderboard;
import dev.necron.tokens.common.runnable.LeaderboardRefreshRunnable;
import dev.necron.tokens.common.runnable.ShopRefreshRunnable;
import dev.necron.tokens.common.runnable.PlayerSaveRunnable;
import dev.necron.tokens.common.shop.ShopManager;
import dev.necron.tokens.common.storage.StorageProvider;
import dev.necron.tokens.bukkit.command.TokenCommand;
import dev.necron.tokens.bukkit.listener.adapter.BukkitListenerAdapter;
import dev.necron.tokens.common.config.ConfigManager;
import dev.necron.tokens.bukkit.shop.loader.BukkitShopLoader;
import dev.necron.tokens.common.util.formatter.TokenFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class NecronTokensPlugin extends JavaPlugin {

    private static final ImmutableList<Class<?>> LISTENERS = ImmutableList.of(
            PlayerListener.class,
            BlockListener.class,
            EntityListener.class,
            InventoryListener.class,
            TokenDropListener.class
    );

    @Override
    public void onEnable() {

        HCore.initialize(this);
        ConfigManager.init();
        StorageProvider.init();
        Leaderboard.init(new BukkitLeaderFinder());
        ShopManager.init(new BukkitShopLoader());
        TokenDropManager.init(new BukkitTokenDropLoader());
        MenuManager.init();

        Blockers.reload();
        MessageCategories.reload();

        startAllRunnable();

        HCommandHandler.register(
                new TokenCommand(),
                new TokenAdminCommand(),
                new TokenShopCommand()
        );

        BukkitListenerAdapter.register(this, LISTENERS);

        new PlaceholderHook(this);

    }

    @Override
    public void onDisable() {

        StorageProvider.shutdown();

    }

    private void startAllRunnable() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new ShopRefreshRunnable(), 20L, 20L);
        scheduler.runTaskTimerAsynchronously(this, new LeaderboardRefreshRunnable(), 20L, 20L);
        scheduler.runTaskTimer(this, new MenuRefreshRunnable(), 20L, 20L);

        PlayerSaveRunnable playerSaveRunnable = new PlayerSaveRunnable();
        scheduler.runTaskTimerAsynchronously(this, playerSaveRunnable, 20L, 20L);
        playerSaveRunnable.onExecute(count -> Bukkit.getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) return;
            LanguageUtil.replaceList(ConfigKeys.Language.AUTO_SAVE_MESSAGE.getValue(),
                    new String[]{"%count%"},
                    new String[]{String.valueOf(count)}
            ).forEach(player::sendMessage);
        }));

        EarnedTokensMessageRunnable earnedTokensMessageRunnable = new EarnedTokensMessageRunnable();
        scheduler.runTaskTimer(this, earnedTokensMessageRunnable, 20L, 20L);
        earnedTokensMessageRunnable.onExecute((timer, reset) -> {
            RewardMessages messages = (RewardMessages) MessageCategories.REWARD_MESSAGES.getInstance();
            Message message = messages.EARNED_TOKENS_LAST_TIME;

            TokenPlayerManager.findAll().forEach(tokenPlayer -> {
                long joinedTimeMillis = tokenPlayer.getJoinedTimeMillis();
                long seconds = (System.currentTimeMillis() - joinedTimeMillis) / 1000;
                if (seconds != 0 && seconds % timer == 0) {
                    Player player = Bukkit.getPlayer(tokenPlayer.getUuid());
                    if (player == null) return;

                    if (reset) tokenPlayer.setEarnedTokens(0);
                    message.execute(player, () -> LanguageUtil.replace(message.getValue(),
                            new String[]{"%amount%"},
                            new String[]{TokenFormatter.format(tokenPlayer.getEarnedTokens())})
                    );
                }
            });
        });
    }

}


