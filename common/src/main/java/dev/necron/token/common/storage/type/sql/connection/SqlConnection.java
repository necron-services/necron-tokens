package dev.necron.token.common.storage.type.sql.connection;

import dev.necron.token.common.storage.type.sql.connection.credentials.SqlCredentials;

import java.sql.Connection;

public interface SqlConnection {

    /**
     * connect to the sql database
     */
    void init(SqlCredentials credentials);

    /**
     * @return the initialized connection
     */
    Connection getConnection();

}
