package dev.necron.common.config;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class ConfigKeys {
    private ConfigKeys() {}

    public static final class Settings {
        private Settings() {}

        public static final ConfigKey<String> LANGUAGE = new ConfigKey<>("en", ConfigType.SETTINGS, "language");

        public static final ConfigKey<String> ADMIN_PERM = new ConfigKey<>("token.admin", ConfigType.SETTINGS, "admin-perm");


    }

    public static final class Language {
        private Language() {}

        public static final ConfigKey<String> PREFIX = new ConfigKey<>("&6NecronToken &8➜", ConfigType.LANGUAGE, "prefix");

        public static final ConfigKey<String> NO_PERMISSION = new ConfigKey<>("&cYou can't have permission to use this command!", ConfigType.LANGUAGE, "no-perm");

        public static final ConfigKey<String> NO_PLAYER = new ConfigKey<>("&cYou must be a player to use this command!", ConfigType.LANGUAGE, "no-player");

        public static final ConfigKey<List<String>> HELP_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "help-message");

    }

}
