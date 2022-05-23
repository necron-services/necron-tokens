package dev.necron.token.common.storage.type.sql.connection.type;

import dev.necron.token.common.storage.type.sql.connection.SqlConnection;

import java.sql.Connection;

public class SqliteConnection implements SqlConnection {

    @Override
    public void init(String host, String port, String database, String username, String password) {

    }

    @Override
    public Connection getConnection() {
        return null;
    }

}
