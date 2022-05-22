package dev.necron.token.common.storage;

import dev.necron.token.common.config.key.ConfigKeys;
import dev.necron.token.common.storage.impl.MongoDBStorage;
import dev.necron.token.common.storage.impl.SQLiteStorage;
import lombok.Getter;
import lombok.Setter;

public final class StorageProvider {

    @Getter @Setter
    private static Storage storage;

    private StorageProvider() {}

    public static void init() {
        StorageType storageType = StorageType.of(ConfigKeys.Settings.STORAGE_TYPE.getValue());
        if (storageType == null) {
            System.out.println("[NecronToken] No storage type found, using default.");
            storageType = StorageType.SQLITE;
        }
        if (storageType == StorageType.CUSTOM) return;
        Storage storage = createImplementation(storageType);
        setStorage(storage);
    }

    public static Storage createImplementation(StorageType storageType) {
        switch (storageType) {
            case MONGODB: return new MongoDBStorage();
            default: return new SQLiteStorage();
        }
    }

}
