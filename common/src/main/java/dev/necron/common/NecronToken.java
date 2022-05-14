package dev.necron.common;

import dev.necron.common.config.ConfigManager;
import dev.necron.common.token.TokenManager;
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
