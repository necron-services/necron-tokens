package dev.necron.bukkit;

import dev.necron.common.NecronToken;
import dev.necron.common.config.Config;
import dev.necron.common.config.ConfigKeys;
import dev.necron.common.config.ConfigManager;
import dev.necron.common.config.ConfigType;
import dev.necron.common.token.TokenManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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


