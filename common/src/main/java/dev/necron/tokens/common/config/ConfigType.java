package dev.necron.tokens.common.config;

import dev.necron.tokens.common.config.key.ConfigKeys;
import lombok.Getter;

public enum ConfigType {
    SETTINGS(ConfigKeys.Settings.class), LANGUAGE(ConfigKeys.Language.class);

    @Getter
    private final Class<?> clazz;

    ConfigType(Class<?> clazz) {
        this.clazz = clazz;
    }

}
