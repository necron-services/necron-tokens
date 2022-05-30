package dev.necron.tokens.common.storage.type.sql;

import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.storage.Storage;
import dev.necron.tokens.common.storage.StorageType;
import dev.necron.tokens.common.storage.type.sql.connection.SqlConnection;
import dev.necron.tokens.common.storage.type.sql.connection.SqlConnectionType;
import dev.necron.tokens.common.storage.type.sql.connection.credentials.SqlCredentials;
import dev.necron.tokens.common.token.TokenPlayer;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SQLStorage implements Storage {

    private SqlConnection connection;

    private final StorageType storageType;

    @Override
    public void init() {
        SqlConnectionType connectionType = SqlConnectionType.of(storageType);
        try {
            connection = (SqlConnection) connectionType.getClazz().newInstance();
            SqlCredentials credentials = SqlCredentials.of(
                    ConfigKeys.Settings.SQL_HOST.getValue(),
                    ConfigKeys.Settings.SQL_PORT.getValue(),
                    ConfigKeys.Settings.SQL_DATABASE.getValue(),
                    ConfigKeys.Settings.SQL_USERNAME.getValue(),
                    ConfigKeys.Settings.SQL_PASSWORD.getValue());
            connection.init(credentials);
        }catch (Exception e) {
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
        return Optional.empty();
    }

    @Override
    public Collection<TokenPlayer> loadPlayers(Collection<UUID> uuids) {
        return null;
    }

    @Override
    public void savePlayer(TokenPlayer player) {

    }

    @Override
    public void savePlayers(Collection<TokenPlayer> players) {

    }

}
