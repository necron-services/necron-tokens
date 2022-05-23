package dev.necron.token.common.storage.type.sql.connection;

import java.sql.Connection;

public interface SqlConnection {

    /**
     * connect to the sql database
     */
    void init(String host, int port, String database, String username, String password);

    /**
     * @return the initialized connection
     */
    Connection getConnection();

}
