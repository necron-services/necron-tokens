package dev.necron.tokens.common.storage;

import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.storage.type.mongodb.MongoStorage;
import dev.necron.tokens.common.storage.type.sql.SQLStorage;
import dev.necron.tokens.common.player.TokenPlayerManager;
import lombok.Getter;
import lombok.Setter;

public final class StorageProvider {

    @Getter
    @Setter
    private static Storage storage;

    private StorageProvider() {
    }

    /**
     * Loads the storage and initializes it
     * if the storage type is custom, use a setter to set the storage
     */
    public static void init() {
        StorageType storageType = StorageType.of(ConfigKeys.Settings.STORAGE_TYPE.getValue());
        if (storageType == null) {
            System.out.println("[NecronTokens] No storage type found, using default.");
            storageType = StorageType.SQLITE;
        }
        if (storageType == StorageType.CUSTOM) return;
        Storage storage = createImplementation(storageType);
        storage.init();
        setStorage(storage);
    }

    /**
     * shutdowns the storage
     */
    public static void shutdown() {
        if (storage != null) {
            storage.savePlayers(TokenPlayerManager.findAll());
            storage.shutdown();
        }
    }

    /**
     * creates a storage implementation
     *
     * @param storageType the storage type
     * @return the storage implementation
     */
    public static Storage createImplementation(StorageType storageType) {
        switch (storageType) {
            case MONGODB:
                return new MongoStorage();
            case SQLITE:
            case MYSQL:
            case MariaDB:
                return new SQLStorage(storageType);
        }
        return null;
    }

}
