package dev.necron.token.common.token;

import lombok.Getter;

import java.util.*;

@Getter
public class TokenPlayerHandler {

    private static final Map<UUID, TokenPlayer> tokenPlayerMap = new HashMap<>();

    /**
     * gets TokenPlayers from map
     * @return the TokenPlayers
     */
    public static Collection<TokenPlayer> findAll() {
        return tokenPlayerMap.values();
    }

    /**
     * get a TokenPlayer from the map
     *
     * @param uuid The UUID of the player
     * @return The TokenPlayer
     */
    public static Optional<TokenPlayer> find(UUID uuid) {
        return Optional.ofNullable(tokenPlayerMap.get(uuid));
    }

    /**
     * add a TokenPlayer to the map
     *
     * @param tokenPlayer The TokenPlayer to add
     */
    public static void put(TokenPlayer tokenPlayer) {
        tokenPlayerMap.put(tokenPlayer.getUuid(), tokenPlayer);
    }

    /**
     * remove a TokenPlayer from the map
     *
     * @param uuid The UUID of the player
     */
    public static void remove(UUID uuid) {
        tokenPlayerMap.remove(uuid);
    }

    /**
     * get the size of the map
     *
     * @return The size of the map
     */
    public static int size() {
        return tokenPlayerMap.size();
    }

}
