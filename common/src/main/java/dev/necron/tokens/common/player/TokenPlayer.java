package dev.necron.tokens.common.player;

import dev.necron.tokens.common.util.formatter.TokenFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter @Setter
public class TokenPlayer {

    private final UUID uuid;
    private long tokens, earnedTokens, joinedTimeMillis;

    /**
     * Adds the given amount of tokens to the player's tokens.
     *
     * @param tokens The amount of tokens to add.
     */
    public void giveTokens(long tokens) {
        this.tokens += tokens;
        earnedTokens += tokens;
    }

    /**
     * Removes the given amount of tokens from the player's tokens.
     *
     * @param tokens The amount of tokens to remove.
     */
    public void takeTokens(long tokens) {
        this.tokens -= tokens;
    }

    /**
     * Gets the amount of tokens the player has.
     *
     * @return Gets player's tokens formatted
     */
    public String getTokensFormatted() {
        return TokenFormatter.format(tokens);
    }

}
