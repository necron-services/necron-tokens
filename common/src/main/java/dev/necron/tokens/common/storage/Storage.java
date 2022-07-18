package dev.necron.tokens.common.storage;

import dev.necron.tokens.common.player.TokenPlayer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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
     * @param uuid the uuid of the player
     */
    Optional<TokenPlayer> loadPlayer(UUID uuid);

    /**
     * save TokenPlayer to storage
     * @param player the TokenPlayer to save
     */
    void savePlayer(TokenPlayer player);

    default void savePlayers(Collection<TokenPlayer> players) {
        players.forEach(this::savePlayer);
    }

    TokenPlayer[] findLeaders(int limit);

}
