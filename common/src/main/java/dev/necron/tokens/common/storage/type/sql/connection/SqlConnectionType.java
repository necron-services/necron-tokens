package dev.necron.tokens.common.storage.type.sql.connection;

import dev.necron.tokens.common.storage.StorageType;
import dev.necron.tokens.common.storage.type.sql.connection.type.MariaConnection;
import dev.necron.tokens.common.storage.type.sql.connection.type.MysqlConnection;
import dev.necron.tokens.common.storage.type.sql.connection.type.SqliteConnection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum SqlConnectionType {

    SQLITE(SqliteConnection.class),
    MYSQL(MysqlConnection.class),
    MARIADB(MariaConnection.class);

    private final Class<?> clazz;

    @Nullable
    public static SqlConnectionType of(String type) {
        for (SqlConnectionType sqlConnectionType : values()) {
            if (sqlConnectionType.name().equalsIgnoreCase(type)) {
                return sqlConnectionType;
            }
        }
        return null;
    }

    @Nullable
    public static SqlConnectionType of(StorageType storageType) {
        return of(storageType.name());
    }

}
