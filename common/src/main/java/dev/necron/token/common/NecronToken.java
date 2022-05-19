package dev.necron.token.common;

import dev.necron.token.common.config.ConfigManager;
import dev.necron.token.common.token.TokenManager;
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
