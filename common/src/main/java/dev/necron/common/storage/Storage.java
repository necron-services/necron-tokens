package dev.necron.common.storage;

import dev.necron.common.token.TokenPlayer;

public interface Storage {

    /**
     * initialize the storage
     */
    void init();

    /**
     * load TokenPlayer from storage
     */
    TokenPlayer loadPlayer(String name);

    /**
     * save TokenPlayer to storage
     */
    void savePlayer(TokenPlayer player);

}
