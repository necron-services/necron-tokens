package dev.necron.token.common.storage.type.sql;

import dev.necron.token.common.config.key.ConfigKeys;
import dev.necron.token.common.storage.Storage;
import dev.necron.token.common.storage.type.sql.connection.SqlConnection;
import dev.necron.token.common.storage.type.sql.connection.SqlConnectionType;
import dev.necron.token.common.token.TokenPlayer;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class SQLStorage implements Storage {

    private SqlConnection connection;

    @Override
    public void init() {
        String type = ConfigKeys.Settings.STORAGE_TYPE.getValue();
        SqlConnectionType connectionType = SqlConnectionType.valueOf(type.toUpperCase());
        try {
            connection = (SqlConnection) connectionType.getClazz().newInstance();
            connection.init(ConfigKeys.Settings.SQL_HOST.getValue(),
                    ConfigKeys.Settings.SQL_PORT.getValue(),
                    ConfigKeys.Settings.SQL_DATABASE.getValue(),
                    ConfigKeys.Settings.SQL_USERNAME.getValue(),
                    ConfigKeys.Settings.SQL_PASSWORD.getValue());
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
