package dev.necron.tokens.common.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
@Getter
public enum StorageType {

    MONGODB("MongoDB"),
    SQLITE("SQLite"),
    MYSQL("MySQL"),
    MariaDB("MariaDB"),
    CUSTOM("Custom");

    private final String name;

    @Nullable
    public static StorageType of(String type) {
        for (StorageType storageType : values()) {
            if (storageType.name().equalsIgnoreCase(type)) {
                return storageType;
            }
        }
        return null;
    }

}
