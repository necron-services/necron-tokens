package dev.necron.tokens.common.player;

import lombok.Getter;

import java.util.*;

@Getter
public class TokenPlayerManager {

    private static final Map<UUID, TokenPlayer> tokenPlayerMap = new HashMap<>();

    /**
     * gets TokenPlayers from map
     * @return the TokenPlayers
     */
    public static Collection<TokenPlayer> findAll() {
        return tokenPlayerMap.values();
    }

    /**
     * find a TokenPlayer from the map
     *
     * @param uuid The UUID of the player
     * @return The TokenPlayer
     */
    public static Optional<TokenPlayer> find(UUID uuid) {
        return Optional.ofNullable(tokenPlayerMap.get(uuid));
    }

    /**
     * get a TokenPlayer from the map
     * if the player doesn't exist, throws exception
     *
     * @param uuid The UUID of the player
     * @return The TokenPlayer
     */
    public static TokenPlayer get(UUID uuid) {
        return find(uuid).orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }

    /**
     * add a TokenPlayer to the map
     *
     * @param tokenPlayer The TokenPlayer to add
     */
    public static void put(TokenPlayer tokenPlayer) {
        tokenPlayer.setJoinedTimeMillis(System.currentTimeMillis());
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
