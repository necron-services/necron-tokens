package dev.necron.token.api.config;

import lombok.Getter;

public enum ConfigType {
    SETTINGS(ConfigKeys.Settings.class), LANGUAGE(ConfigKeys.Language.class);

    @Getter
    private final Class<?> clazz;

    ConfigType(Class<?> clazz) {
        this.clazz = clazz;
    }

}
