package dev.necron.tokens.bukkit.hook;

import dev.necron.tokens.common.leaderboard.Leaderboard;
import dev.necron.tokens.common.leaderboard.leader.Leader;
import dev.necron.tokens.common.player.TokenPlayer;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.util.formatter.TokenFormatter;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PlaceholderHook extends PlaceholderExpansion {

    private final Plugin plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "tokenplugin";
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
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {

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
                if (type.equalsIgnoreCase("name")) return leader.getName();
                if (type.equalsIgnoreCase("tokens")) return TokenFormatter.format(leader.getTokens());
            }
            return type.equalsIgnoreCase("name") ? "N/A" : "0";
        }

        return super.onRequest(player, params);
    }

}
