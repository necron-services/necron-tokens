package dev.necron.tokens.bukkit.hook;

import dev.necron.tokens.common.leaderboard.Leaderboard;
import dev.necron.tokens.common.leaderboard.leader.Leader;
import dev.necron.tokens.common.player.TokenPlayer;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.util.formatter.TokenFormatter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class PlaceholderHook extends PlaceholderExpansion {

    private final Plugin plugin;

    public PlaceholderHook(Plugin plugin) {
        this.plugin = plugin;
        register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "NecronTokens";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("tokens")) {
            UUID playerUUID = player.getUniqueId();
            Optional<TokenPlayer> optionalTokenPlayer = TokenPlayerManager.find(playerUUID);
            if (optionalTokenPlayer.isPresent()) {
                TokenPlayer tokenPlayer = optionalTokenPlayer.get();
                return tokenPlayer.getTokensFormatted();
            }
            return "0";
        }else if (params.equalsIgnoreCase("earned_tokens")) {
            UUID playerUUID = player.getUniqueId();
            Optional<TokenPlayer> optionalTokenPlayer = TokenPlayerManager.find(playerUUID);
            if (optionalTokenPlayer.isPresent()) {
                TokenPlayer tokenPlayer = optionalTokenPlayer.get();
                return TokenFormatter.format(tokenPlayer.getEarnedTokens());
            }
            return "0";
        } else if (params.startsWith("leader")) {
            String[] split = params.split("_");
            if (split.length != 3) throw new IllegalArgumentException("Invalid placeholder: " + params);
            String type = split[1];
            int leaderLine = Integer.parseInt(split[2]) - 1;
            Optional<Leader> optionalLeader = Leaderboard.find(leaderLine);
            if (optionalLeader.isPresent()) {
                Leader leader = optionalLeader.get();
                return type.equalsIgnoreCase("name")
                        ? leader.getName()
                        : TokenFormatter.format(leader.getTokens());
            }
            return type.equalsIgnoreCase("name") ? "N/A" : "0";
        }
        return super.onPlaceholderRequest(player, params);
    }

}
