package dev.necron.tokens.common.leaderboard;

import dev.necron.tokens.common.leaderboard.leader.Leader;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.leaderboard.leader.finder.LeaderFinder;
import dev.necron.tokens.common.storage.Storage;
import dev.necron.tokens.common.storage.StorageProvider;
import dev.necron.tokens.common.player.TokenPlayer;

import java.util.Optional;
import java.util.UUID;

public class Leaderboard {

    private static LeaderFinder leaderFinder;
    private static Leader[] leaders;
    private static long regenTime;

    public static void init(LeaderFinder leaderFinder) {
        Leaderboard.leaderFinder = leaderFinder;
        reload();
    }

    public static void reload() {
        Storage storage = StorageProvider.getStorage();

        int maxCount = ConfigKeys.Settings.LEADERBOARD_MAX_COUNT.getValue();
        TokenPlayer[] tokenPlayers = storage.findLeaders(maxCount);

        leaders = new Leader[tokenPlayers.length];
        for (int i = 0; i < tokenPlayers.length; i++) {
            TokenPlayer tokenPlayer = tokenPlayers[i];
            if (tokenPlayer == null) break;
            UUID playerUUID = tokenPlayer.getUuid();
            leaders[i] = new Leader(
                    playerUUID,
                    leaderFinder.find(playerUUID),
                    tokenPlayer.getTokens()
            );
        }

        regenTime = System.currentTimeMillis() + (ConfigKeys.Settings.LEADERBOARD_REGEN_TIME.getValue() * 1000);
    }

    public static Optional<Leader> find(int index) {
        if (leaders == null || index < 0 || index >= leaders.length) return Optional.empty();
        return Optional.ofNullable(leaders[index]);
    }

    public static int count() {
        return leaders != null ? leaders.length : 0;
    }

    public static long getRegenTime() {
        return (regenTime - System.currentTimeMillis()) / 1000;
    }

}
