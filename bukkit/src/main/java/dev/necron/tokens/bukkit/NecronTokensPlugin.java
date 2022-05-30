package dev.necron.tokens.bukkit;

import co.aikar.commands.BukkitCommandManager;
import dev.necron.tokens.bukkit.drop.loader.BukkitTokenDropLoader;
import dev.necron.tokens.bukkit.listener.InventoryListener;
import dev.necron.tokens.bukkit.menu.handler.MenuHandler;
import dev.necron.tokens.common.NecronTokens;
import dev.necron.tokens.common.drop.handler.TokenDropHandler;
import dev.necron.tokens.common.runnable.ShopRefreshRunnable;
import dev.necron.tokens.common.shop.handler.ShopHandler;
import dev.necron.tokens.common.storage.StorageProvider;
import dev.necron.tokens.bukkit.command.TokenCommand;
import dev.necron.tokens.bukkit.listener.BlockListener;
import dev.necron.tokens.bukkit.listener.EntityListener;
import dev.necron.tokens.bukkit.listener.PlayerListener;
import dev.necron.tokens.bukkit.listener.adapter.BukkitListenerAdapter;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.config.handler.ConfigHandler;
import dev.necron.tokens.common.config.ConfigType;
import dev.necron.tokens.bukkit.shop.loader.BukkitShopLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NecronTokensPlugin extends JavaPlugin {

    @Getter
    private static NecronTokensPlugin instance;

    @Override
    public void onEnable() {

        instance = this;

        reloadConfigs();
        StorageProvider.init();
        ShopHandler.init(new BukkitShopLoader());
        TokenDropHandler.init(new BukkitTokenDropLoader());
        MenuHandler.init();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ShopRefreshRunnable(), 20L, 20L);

        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new TokenCommand());

        BukkitListenerAdapter.register(this,
                PlayerListener.class,
                BlockListener.class,
                EntityListener.class,
                InventoryListener.class);

    }

    @Override
    public void onDisable() {

        StorageProvider.shutdown();

    }

    public void reloadConfigs() {
        ClassLoader classLoader = NecronTokens.class.getClassLoader();
        ConfigHandler.createConfig("settings",
                "settings.yml",
                classLoader.getResourceAsStream("settings.yml")).ifPresent(config -> {
                    ConfigHandler.put(ConfigType.SETTINGS, config);
                    ConfigHandler.putNodesToClass(config, ConfigType.SETTINGS.getClazz(), true);
        });
        String language = ConfigKeys.Settings.LANGUAGE.getValue();
        ConfigHandler.createConfig("language",
                "language/language_" + language + ".yml",
                classLoader.getResourceAsStream("language/language_" + language + ".yml")).ifPresent(config -> {
            ConfigHandler.put(ConfigType.LANGUAGE, config);
            ConfigHandler.putNodesToClass(config, ConfigType.LANGUAGE.getClazz(), true);
        });
    }

}


