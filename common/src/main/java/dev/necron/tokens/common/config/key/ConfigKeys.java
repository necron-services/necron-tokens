package dev.necron.tokens.common.config.key;

import com.google.common.collect.ImmutableList;
import dev.necron.tokens.common.config.ConfigType;

import java.util.List;

public final class ConfigKeys {
    private ConfigKeys() {
    }

    public static final class Settings {
        private Settings() {
        }

        public static final ConfigKey<String> LANGUAGE = new ConfigKey<>("en", ConfigType.SETTINGS, "language");

        public static final ConfigKey<String> ADMIN_PERM = new ConfigKey<>("token.admin", ConfigType.SETTINGS, "admin-perm");

        public static final ConfigKey<Boolean> AUTO_SAVE_USERS_ENABLED = new ConfigKey<>(true, ConfigType.SETTINGS, "auto-save-users", "enabled");
        public static final ConfigKey<Integer> AUTO_SAVE_USERS_INTERVAL = new ConfigKey<>(60, ConfigType.SETTINGS, "auto-save-users", "interval");

        public static final ConfigKey<Integer> LEADERBOARD_MAX_COUNT = new ConfigKey<>(10, ConfigType.SETTINGS, "leaderboard", "max-count");
        public static final ConfigKey<Integer> LEADERBOARD_REGEN_TIME = new ConfigKey<>(60, ConfigType.SETTINGS, "leaderboard", "regen-time");

        public static final ConfigKey<Boolean> SHOP_CLOSE_MENU_ON_BUY = new ConfigKey<>(true, ConfigType.SETTINGS, "shop", "close-menu-on-buy");
        public static final ConfigKey<Boolean> SHOP_SOUNDS_ENABLED = new ConfigKey<>(true, ConfigType.SETTINGS, "shop", "sounds", "enabled");
        public static final ConfigKey<String> SHOP_BUY_SOUND = new ConfigKey<>("", ConfigType.SETTINGS, "shop", "sounds", "buy-sound");
        public static final ConfigKey<String> SHOP_CANNOT_BUY_SOUND = new ConfigKey<>("", ConfigType.SETTINGS, "shop", "sounds", "cannot-buy-sound");

        public static final ConfigKey<Boolean> WORLD_BLOCKER_ENABLED = new ConfigKey<>(true, ConfigType.SETTINGS, "world-blocker", "enabled");
        public static final ConfigKey<String> WORLD_BLOCKER_TYPE = new ConfigKey<>("world", ConfigType.SETTINGS, "world-blocker", "type");
        public static final ConfigKey<List<String>> BLOCKED_WORLDS = new ConfigKey<>(ImmutableList.of(), ConfigType.SETTINGS, "world-blocker", "worlds");

        public static final ConfigKey<Boolean> EARN_TOKENS_SOUND_ENABLED = new ConfigKey<>(true, ConfigType.SETTINGS, "earn-token-sounds", "enabled");
        public static final ConfigKey<String> EARN_TOKENS_FROM_ENTITY_SOUND = new ConfigKey<>("", ConfigType.SETTINGS, "earn-token-sounds", "from-entity");
        public static final ConfigKey<String> EARN_TOKENS_FROM_BLOCK_SOUND = new ConfigKey<>("", ConfigType.SETTINGS, "earn-token-sounds", "from-block");

        public static final ConfigKey<Boolean> EARNED_TOKENS_MESSAGE_ENABLED = new ConfigKey<>(true, ConfigType.SETTINGS, "earned-tokens-message", "enabled");
        public static final ConfigKey<Integer> EARNED_TOKENS_MESSAGE_TIMER = new ConfigKey<>(300, ConfigType.SETTINGS, "earned-tokens-message", "timer");
        public static final ConfigKey<Boolean> RESET_PLAYERS_EARNED_TOKENS = new ConfigKey<>(true, ConfigType.SETTINGS, "earned-tokens-message", "reset-players-earned-tokens");

        public static final ConfigKey<String> STORAGE_TYPE = new ConfigKey<>("sqlite", ConfigType.SETTINGS, "storage", "type");

        public static final ConfigKey<String> MONGODB_URL = new ConfigKey<>("mongodb://localhost:27017", ConfigType.SETTINGS, "storage", "mongodb", "url");
        public static final ConfigKey<String> MONGODB_DB = new ConfigKey<>("test", ConfigType.SETTINGS, "storage", "mongodb", "db");
        public static final ConfigKey<String> MONGODB_COLLECTION = new ConfigKey<>("collection", ConfigType.SETTINGS, "storage", "mongodb", "collection");

        public static final ConfigKey<String> SQL_HOST = new ConfigKey<>("localhost", ConfigType.SETTINGS, "storage", "sql", "host");
        public static final ConfigKey<Integer> SQL_PORT = new ConfigKey<>(3306, ConfigType.SETTINGS, "storage", "sql", "port");
        public static final ConfigKey<String> SQL_DATABASE = new ConfigKey<>("test", ConfigType.SETTINGS, "storage", "sql", "database");
        public static final ConfigKey<String> SQL_USERNAME = new ConfigKey<>("root", ConfigType.SETTINGS, "storage", "sql", "username");
        public static final ConfigKey<String> SQL_PASSWORD = new ConfigKey<>("", ConfigType.SETTINGS, "storage", "sql", "password");

    }

    public static final class Language {
        private Language() {
        }

        public static final ConfigKey<String> PREFIX = new ConfigKey<>("&6NecronToken &8»", ConfigType.LANGUAGE, "prefix");

        public static final ConfigKey<String> RELOADED = new ConfigKey<>("&aSuccessfully reloaded.", ConfigType.LANGUAGE, "reloaded");

        public static final ConfigKey<String> NO_PERMISSION = new ConfigKey<>("&cYou can't have permission to use this command!", ConfigType.LANGUAGE, "no-perm");
        public static final ConfigKey<String> NO_PLAYER = new ConfigKey<>("&cYou must be a player to use this command!", ConfigType.LANGUAGE, "no-player");
        public static final ConfigKey<String> PLAYER_NOT_FOUND = new ConfigKey<>("&cPlayer not found!", ConfigType.LANGUAGE, "player-not-found");

        public static final ConfigKey<List<String>> HELP_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "help-message");
        public static final ConfigKey<List<String>> HELP_ADMIN_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "help-admin-message");

        public static final ConfigKey<List<String>> AUTO_SAVE_MESSAGE = new ConfigKey<>(ImmutableList.of(), ConfigType.LANGUAGE, "auto-save-message");

        public static final ConfigKey<String> TOKEN_MESSAGES_TYPE = new ConfigKey<>("actionbar", ConfigType.LANGUAGE, "token-messages", "type");
        public static final ConfigKey<String> TOKEN_RECEIVED_MESSAGE = new ConfigKey<>("&aYou received &6%amount% &a%token%!", ConfigType.LANGUAGE, "token-messages", "token-received");
        public static final ConfigKey<String> TOKEN_SENT_MESSAGE = new ConfigKey<>("&aYou sent &6%amount% &a%token%!", ConfigType.LANGUAGE, "token-messages", "token-sent");
        public static final ConfigKey<String> TOKEN_SHOW_MESSAGE = new ConfigKey<>("&aYou have &6%amount% &a%token%!", ConfigType.LANGUAGE, "token-messages", "token-show");
        public static final ConfigKey<String> TOKEN_NOT_ENOUGH_TOKENS_MESSAGE = new ConfigKey<>("&cYou don't have enough tokens!", ConfigType.LANGUAGE, "token-messages", "error-messages", "not-enough-tokens");
        public static final ConfigKey<String> CANNOT_SEND_TO_YOURSELF_MESSAGE = new ConfigKey<>("&cYou can't send tokens to yourself!", ConfigType.LANGUAGE, "token-messages", "error-messages", "cannot-send-to-yourself");
        public static final ConfigKey<String> AMOUNT_NOT_VALID_MESSAGE = new ConfigKey<>("&cAmount not valid!", ConfigType.LANGUAGE, "token-messages", "error-messages", "amount-not-valid");
        public static final ConfigKey<String> ADMIN_TOKEN_ADDED_MESSAGE = new ConfigKey<>("&aAdded &6%amount% &a%token%!", ConfigType.LANGUAGE, "token-messages", "admin-messages", "token-added");
        public static final ConfigKey<String> ADMIN_TOKEN_REMOVED_MESSAGE = new ConfigKey<>("&aRemoved &6%amount% &a%token%!", ConfigType.LANGUAGE, "token-messages", "admin-messages", "token-removed");
        public static final ConfigKey<String> ADMIN_TOKEN_SET_MESSAGE = new ConfigKey<>("&aSet &6%amount% &a%token%!", ConfigType.LANGUAGE, "token-messages", "admin-messages", "token-set");

        public static final ConfigKey<String> REWARD_MESSAGES_TYPE = new ConfigKey<>("actionbar", ConfigType.LANGUAGE, "reward-messages", "type");
        public static final ConfigKey<String> EARNED_TOKENS_FROM_BLOCK_MESSAGE = new ConfigKey<>("&aYou earned &6%amount% &a%token%!", ConfigType.LANGUAGE, "reward-messages", "earned-tokens-from-block");
        public static final ConfigKey<String> EARNED_TOKENS_FROM_ENTITY_MESSAGE = new ConfigKey<>("&aYou earned &6%amount% &a%token%!", ConfigType.LANGUAGE, "reward-messages", "earned-tokens-from-entity");
        public static final ConfigKey<String> EARNED_TOKENS_LAST_TIME_MESSAGE = new ConfigKey<>("&aYou earned &6%amount% &a%token%!", ConfigType.LANGUAGE, "reward-messages", "earned-tokens-last-time");

        public static final ConfigKey<String> SHOP_MESSAGES_TYPE = new ConfigKey<>("actionbar", ConfigType.LANGUAGE, "shop-messages", "type");
        public static final ConfigKey<String> INVENTORY_FULL_MESSAGE = new ConfigKey<>("&cYour inventory is full!", ConfigType.LANGUAGE, "shop-messages", "inventory-full");
        public static final ConfigKey<String> NOT_ENOUGH_THINGS_MESSAGE = new ConfigKey<>("&cYou don't have enough things!", ConfigType.LANGUAGE, "shop-messages", "not-enough-things");
        public static final ConfigKey<String> BOUGHT_SHOP_ITEM_MESSAGE = new ConfigKey<>("&aYou bought &6%amount% &a%token%!", ConfigType.LANGUAGE, "shop-messages", "bought-shop-item");
        public static final ConfigKey<String> SHOP_HAS_BEEN_REFRESHED_MESSAGE = new ConfigKey<>("&aShop has been refreshed!", ConfigType.LANGUAGE, "shop-messages", "shop-has-been-refreshed");
        public static final ConfigKey<String> SHOPS_HAS_BEEN_REFRESHED_MESSAGE = new ConfigKey<>("&aShops has been refreshed!", ConfigType.LANGUAGE, "shop-messages", "shops-has-been-refreshed");

    }
}