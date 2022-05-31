package dev.necron.tokens.common.storage.type.sql.connection.type;

import dev.necron.tokens.common.storage.type.sql.connection.SqlConnection;
import dev.necron.tokens.common.storage.type.sql.connection.credentials.SqlCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlConnection implements SqlConnection {

    private Connection connection;

    @Override
    public void init(SqlCredentials credentials) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabase(),
                    credentials.getUsername(),
                    credentials.getPassword());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
