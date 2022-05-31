package dev.necron.tokens.common.storage.type.sql.connection.type;

import dev.necron.tokens.common.storage.type.sql.connection.SqlConnection;
import dev.necron.tokens.common.storage.type.sql.connection.credentials.SqlCredentials;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection implements SqlConnection {

    private Connection connection;

    @Override
    public void init(SqlCredentials credentials) {
        final File file = new File("plugins/NecronTokens/storage/token_player.db");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
