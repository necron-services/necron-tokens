package dev.necron.token.bukkit;

import co.aikar.commands.BukkitCommandManager;
import dev.necron.token.bukkit.command.TokenCommand;
import dev.necron.token.bukkit.listener.BlockListener;
import dev.necron.token.bukkit.listener.EntityListener;
import dev.necron.token.bukkit.listener.PlayerListener;
import dev.necron.token.bukkit.listener.adapter.BukkitListenerAdapter;
import dev.necron.token.api.NecronToken;
import dev.necron.token.api.config.Config;
import dev.necron.token.api.config.ConfigKeys;
import dev.necron.token.api.config.ConfigManager;
import dev.necron.token.api.config.ConfigType;
import dev.necron.token.api.token.TokenManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class NecronTokenPlugin extends JavaPlugin implements NecronToken {

    @Getter
    private static NecronTokenPlugin instance;

    @Getter
    private ConfigManager configManager;
    @Getter
    private TokenManager tokenManager;

    @Override
    public void onEnable() {

        instance = this;

        configManager = new ConfigManager();
        reloadConfigs();
        tokenManager = new TokenManager();

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
        Config settingsConfig = configManager.createConfig("settings",
                getDataFolder().getAbsolutePath() + "/settings.yml",
                classLoader.getResourceAsStream("settings.yml"));
        configManager.addConfig(ConfigType.SETTINGS, settingsConfig);
        configManager.putNodesToClass(settingsConfig, ConfigType.SETTINGS.getClazz(), true);
        String language = ConfigKeys.Settings.LANGUAGE.getValue();
        Config languageConfig = configManager.createConfig("language",
                getDataFolder().getAbsolutePath() + "/language/language_" + language + ".yml",
                classLoader.getResourceAsStream("language/language_" + language + ".yml"));
        configManager.addConfig(ConfigType.LANGUAGE, languageConfig);
        configManager.putNodesToClass(languageConfig, ConfigType.LANGUAGE.getClazz(), true);
    }


}


