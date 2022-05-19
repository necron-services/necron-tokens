package dev.necron.token.api;

import dev.necron.token.api.config.ConfigManager;
import dev.necron.token.api.token.TokenManager;
import org.jetbrains.annotations.NotNull;

public interface NecronToken {

    /**
     * @return The config manager for this plugin
     */
    @NotNull
    ConfigManager getConfigManager();

    /**
     * @return The token manager for this plugin
     */
    @NotNull
    TokenManager getTokenManager();

}
