package dev.necron.tokens.common.storage.type.sql.connection.credentials;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class SqlCredentials {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    public static SqlCredentials of(String host, int port, String database, String username, String password) {
        return new SqlCredentials(host, port, database, username, password);
    }

}
