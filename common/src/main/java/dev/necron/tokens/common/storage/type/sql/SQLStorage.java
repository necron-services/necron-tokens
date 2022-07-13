package dev.necron.tokens.common.storage.type.sql;

import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.storage.Storage;
import dev.necron.tokens.common.storage.StorageType;
import dev.necron.tokens.common.storage.type.sql.connection.SqlConnection;
import dev.necron.tokens.common.storage.type.sql.connection.SqlConnectionType;
import dev.necron.tokens.common.storage.type.sql.connection.credentials.SqlCredentials;
import dev.necron.tokens.common.player.TokenPlayer;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SQLStorage implements Storage {

    private SqlConnection connection;

    private final StorageType storageType;

    @Override
    public void init() {
        SqlConnectionType connectionType = SqlConnectionType.of(storageType);
        SqlCredentials credentials = SqlCredentials.of(
                ConfigKeys.Settings.SQL_HOST.getValue(),
                ConfigKeys.Settings.SQL_PORT.getValue(),
                ConfigKeys.Settings.SQL_DATABASE.getValue(),
                ConfigKeys.Settings.SQL_USERNAME.getValue(),
                ConfigKeys.Settings.SQL_PASSWORD.getValue()
        );
        try {
            connection = connectionType.getClazz()
                    .asSubclass(SqlConnection.class)
                    .newInstance();
            connection.init(credentials);
            connection.getConnection()
                    .createStatement()
                    .executeUpdate(
                            "CREATE TABLE IF NOT EXISTS token_user(" +
                                    "uuid VARCHAR(255) NOT NULL," +
                                    "tokens VARCHAR(255) NOT NULL," +
                                    "PRIMARY KEY (uuid)" +
                                    ");"
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        try {
            connection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<TokenPlayer> loadPlayer(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            TokenPlayer player = new TokenPlayer(uuid);
            try {
                Connection connection = this.connection.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM token_user WHERE uuid = ?;");
                statement.setString(1, uuid.toString());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    player.setTokens(resultSet.getInt("tokens"));
                } else {
                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO token_user (uuid, tokens) VALUES (?, ?);");
                    insertStatement.setString(1, uuid.toString());
                    insertStatement.setLong(2, 0);
                    insertStatement.executeUpdate();
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.of(player);
        }).join();
    }

    @Override
    public void savePlayer(TokenPlayer player) {
        CompletableFuture.runAsync(() -> {
            try {
                Connection connection = this.connection.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE token_user SET tokens = ? WHERE uuid = ?;");
                statement.setLong(1, player.getTokens());
                statement.setString(2, player.getUuid().toString());
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void savePlayers(Collection<TokenPlayer> players) {
        try {
            Connection connection = this.connection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE token_user SET tokens = ? WHERE uuid = ?;");
            for (TokenPlayer player : players) {
                statement.setLong(1, player.getTokens());
                statement.setString(2, player.getUuid().toString());
                statement.executeUpdate();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TokenPlayer[] findLeaderboard(int limit) {
        return CompletableFuture.supplyAsync(() -> {
            TokenPlayer[] players = new TokenPlayer[limit];
            try {
                Connection connection = this.connection.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM token_user ORDER BY tokens DESC LIMIT ?;");
                statement.setInt(1, limit);
                ResultSet resultSet = statement.executeQuery();
                int i = 0;
                while (resultSet.next()) {
                    TokenPlayer player = new TokenPlayer(UUID.fromString(resultSet.getString("uuid")));
                    player.setTokens(resultSet.getInt("tokens"));
                    players[i] = player;
                    i++;
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return players;
        }).join();
    }

}
