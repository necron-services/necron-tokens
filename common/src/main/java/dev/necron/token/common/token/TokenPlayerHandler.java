package dev.necron.token.common.token;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
public class TokenPlayerHandler {

    private static final Map<UUID, TokenPlayer> tokenPlayerMap = new HashMap<>();

    /**
     * get a TokenPlayer from the map
     *
     * @param uuid The UUID of the player
     * @return The TokenPlayer
     */
    public Optional<TokenPlayer> getTokenPlayer(UUID uuid) {
        return Optional.ofNullable(tokenPlayerMap.get(uuid));
    }

    /**
     * add a TokenPlayer to the map
     *
     * @param tokenPlayer The TokenPlayer to add
     */
    public void put(TokenPlayer tokenPlayer) {
        tokenPlayerMap.put(tokenPlayer.getPlayerUUID(), tokenPlayer);
    }

    /**
     * remove a TokenPlayer from the map
     *
     * @param playerUUID The UUID of the player
     */
    public void remove(UUID playerUUID) {
        tokenPlayerMap.remove(playerUUID);
    }

}
