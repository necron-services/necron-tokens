package dev.necron.token.common.token;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class TokenManager {

    private final Map<UUID, TokenPlayer> tokenPlayerMap = new HashMap<>();

    /**
     * get a TokenPlayer from the map
     *
     * @param uuid The UUID of the player
     * @return The TokenPlayer
     */
    @Nullable
    public TokenPlayer getTokenPlayer(UUID uuid) {
        return tokenPlayerMap.get(uuid);
    }

    /**
     * add a TokenPlayer to the map
     *
     * @param tokenPlayer The TokenPlayer to add
     */
    public void addTokenPlayer(TokenPlayer tokenPlayer) {
        tokenPlayerMap.put(tokenPlayer.getPlayerUUID(), tokenPlayer);
    }

    /**
     * remove a TokenPlayer from the map
     *
     * @param playerUUID The UUID of the player
     */
    public void removeTokenPlayer(UUID playerUUID) {
        tokenPlayerMap.remove(playerUUID);
    }

    /**
     * check if a TokenPlayer is in the map
     *
     * @param playerUUID The UUID of the player
     * @return true if the player is in the map, false if not
     */
    public boolean containsTokenPlayer(UUID playerUUID) {
        return tokenPlayerMap.containsKey(playerUUID);
    }

}
