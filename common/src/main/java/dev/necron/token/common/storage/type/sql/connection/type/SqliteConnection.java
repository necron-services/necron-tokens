package dev.necron.token.common.storage.type.sql.connection.type;

import dev.necron.token.common.storage.type.sql.connection.SqlConnection;
import dev.necron.token.common.storage.type.sql.connection.credentials.SqlCredentials;

import java.sql.Connection;

public class SqliteConnection implements SqlConnection {


    @Override
    public void init(SqlCredentials credentials) {

    }

    @Override
    public Connection getConnection() {
        return null;
    }

}
