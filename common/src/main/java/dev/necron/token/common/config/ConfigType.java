package dev.necron.token.common.config;

import dev.necron.token.common.config.key.ConfigKeys;
import lombok.Getter;

public enum ConfigType {
    SETTINGS(ConfigKeys.Settings.class), LANGUAGE(ConfigKeys.Language.class);

    @Getter
    private final Class<?> clazz;

    ConfigType(Class<?> clazz) {
        this.clazz = clazz;
    }

}
