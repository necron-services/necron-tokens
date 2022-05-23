package dev.necron.token.common.storage.type.sql.connection;

import dev.necron.token.common.storage.type.sql.connection.type.MariaConnection;
import dev.necron.token.common.storage.type.sql.connection.type.MysqlConnection;
import dev.necron.token.common.storage.type.sql.connection.type.SqliteConnection;
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
            if (sqlConnectionType.name().equalsIgnoreCase(type.toUpperCase(Locale.ROOT))) {
                return sqlConnectionType;
            }
        }
        return null;
    }

}
