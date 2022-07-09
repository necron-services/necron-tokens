package dev.necron.tokens.common.storage;

import dev.necron.tokens.common.player.TokenPlayer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
     * load TokenPlayers from storage
     * @param uuids the uuids to load
     * @return the loaded TokenPlayers
     */
    default Collection<TokenPlayer> loadPlayers(Collection<UUID> uuids) {
        return uuids.stream()
                .map(this::loadPlayer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    /**
     * save TokenPlayer to storage
     * @param player the TokenPlayer to save
     */
    void savePlayer(TokenPlayer player);

    /**
     * save TokenPlayers to storage
     * @param players the TokenPlayers to save
     */
    default void savePlayers(Collection<TokenPlayer> players) {
        players.forEach(this::savePlayer);
    }

    TokenPlayer[] findLeaderboard(int limit);

}
