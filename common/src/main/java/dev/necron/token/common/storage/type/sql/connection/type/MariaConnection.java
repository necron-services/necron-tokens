package dev.necron.token.common.storage.type.sql.connection.type;

import dev.necron.token.common.storage.type.sql.connection.SqlConnection;

import java.sql.Connection;

public class MariaConnection implements SqlConnection {
    @Override
    public Connection connect(String host, String port, String database, String username, String password) {
        return null;
    }
}
