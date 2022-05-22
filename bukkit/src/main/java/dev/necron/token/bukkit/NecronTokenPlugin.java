package dev.necron.token.bukkit;

import co.aikar.commands.BukkitCommandManager;
import dev.necron.token.common.shop.handler.ShopHandler;
import dev.necron.token.common.storage.StorageProvider;
import dev.necron.token.bukkit.command.TokenCommand;
import dev.necron.token.bukkit.listener.BlockListener;
import dev.necron.token.bukkit.listener.EntityListener;
import dev.necron.token.bukkit.listener.PlayerListener;
import dev.necron.token.bukkit.listener.adapter.BukkitListenerAdapter;
import dev.necron.token.common.NecronToken;
import dev.necron.token.common.config.key.ConfigKeys;
import dev.necron.token.common.config.handler.ConfigHandler;
import dev.necron.token.common.config.ConfigType;
import dev.necron.token.bukkit.shop.loader.BukkitShopLoader;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class NecronTokenPlugin extends JavaPlugin {

    @Getter
    private static NecronTokenPlugin instance;

    @Override
    public void onEnable() {

        instance = this;

        reloadConfigs();
        StorageProvider.init();
        ShopHandler.init(new BukkitShopLoader());

        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new TokenCommand());

        BukkitListenerAdapter.register(this,
                PlayerListener.class,
                BlockListener.class,
                EntityListener.class);

    }

    @Override
    public void onDisable() {

    }

    public void reloadConfigs() {
        ClassLoader classLoader = NecronToken.class.getClassLoader();
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


