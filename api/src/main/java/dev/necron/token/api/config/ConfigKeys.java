package dev.necron.token.api.config;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class ConfigKeys {
    private ConfigKeys() {
    }

    public static final class Settings {
        private Settings() {
        }

        public static final ConfigKey<String> LANGUAGE = new ConfigKey<>("en", ConfigType.SETTINGS, "language");

        public static final ConfigKey<String> ADMIN_PERM = new ConfigKey<>("token.admin", ConfigType.SETTINGS, "admin-perm");

        public static final ConfigKey<String> STORAGE_TYPE = new ConfigKey<>("sqlite", ConfigType.SETTINGS, "storage", "type");

        public static final ConfigKey<String> MONGODB_URL = new ConfigKey<>("mongodb://localhost:27017", ConfigType.SETTINGS, "storage", "mongodb", "url");
        public static final ConfigKey<String> MONGODB_DB = new ConfigKey<>("test", ConfigType.SETTINGS, "storage", "mongodb", "db");
        public static final ConfigKey<String> MONGODB_COLLECTION = new ConfigKey<>("collection", ConfigType.SETTINGS, "storage", "mongodb", "collection");

    }

    public static final class Language {
        private Language() {
        }

        public static final ConfigKey<String> PREFIX = new ConfigKey<>("&6NecronToken &8»", ConfigType.LANGUAGE, "prefix");

        public static final ConfigKey<String> NO_PERMISSION = new ConfigKey<>("&cYou can't have permission to use this command!", ConfigType.LANGUAGE, "no-perm");
        public static final ConfigKey<String> NO_PLAYER = new ConfigKey<>("&cYou must be a player to use this command!", ConfigType.LANGUAGE, "no-player");
        public static final ConfigKey<String> PLAYER_NOT_FOUND = new ConfigKey<>("&cPlayer not found!", ConfigType.LANGUAGE, "player-not-found");

        public static final ConfigKey<String> RELOADED = new ConfigKey<>("&aSuccessfully reloaded.", ConfigType.LANGUAGE, "reloaded");

        public static final ConfigKey<List<String>> HELP_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "help-message");

    }
}