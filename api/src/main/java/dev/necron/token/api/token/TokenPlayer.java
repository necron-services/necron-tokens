package dev.necron.token.api.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class TokenPlayer {

    private final UUID playerUUID;
    private long tokens;

    /**
     * Adds the given amount of tokens to the player's tokens.
     *
     * @param tokens The amount of tokens to add.
     */
    public void addTokens(long tokens) {
        this.tokens += tokens;
    }

    /**
     * Removes the given amount of tokens from the player's tokens.
     *
     * @param tokens The amount of tokens to remove.
     */
    public void removeTokens(long tokens) {
        this.tokens -= tokens;
    }

}
