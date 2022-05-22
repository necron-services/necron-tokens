package dev.necron.token.common.storage;

import dev.necron.token.common.token.TokenPlayer;

public interface Storage {

    /**
     * initialize the storage
     */
    void init();

    /**
     * shutdown the storage
     */
    void shutdown();

    /**
     * load TokenPlayer from storage
     */
    TokenPlayer loadPlayer(String name);

    /**
     * save TokenPlayer to storage
     */
    void savePlayer(TokenPlayer player);

}
