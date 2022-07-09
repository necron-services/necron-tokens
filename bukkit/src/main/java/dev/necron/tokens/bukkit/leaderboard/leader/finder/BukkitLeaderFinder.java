package dev.necron.tokens.bukkit.leaderboard.leader.finder;

import dev.necron.tokens.common.leaderboard.leader.finder.LeaderFinder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class BukkitLeaderFinder implements LeaderFinder {
    @Override
    public String find(UUID leaderUUID) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(leaderUUID);
        return player.getName();
    }
}
