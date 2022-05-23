package dev.necron.token.common.drop.handler;

import dev.necron.token.common.drop.TokenDrop;
import dev.necron.token.common.drop.TokenDropType;
import dev.necron.token.common.drop.loader.TokenDropLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TokenDropHandler {

    private static final Map<TokenDropType, TokenDrop[]> drops = new HashMap<>();

    /**
     * Initialize the drop handler
     * @param dropLoader The drop loader
     */
    public static void init(TokenDropLoader dropLoader) {
        try {
            TokenDropHandler.drops.putAll(dropLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Register a drops
     * @param type The type of drops
     * @param drops The drops
     */
    public static void register(TokenDropType type, TokenDrop... drops) {
        TokenDropHandler.drops.put(type, drops);
    }

    /**
     * Unregister a drops
     * @param type The type of drops
     */
    public static void unregister(TokenDropType type) {
        TokenDropHandler.drops.remove(type);
    }

    /**
     * Put a drop
     * @param type The type of drops
     * @param drop The drop
     */
    public static void put(TokenDropType type, TokenDrop drop) {
        Optional<TokenDrop[]> tokenDrops = find(type);
        tokenDrops.ifPresentOrElse(drops -> drops[drops.length - 1] = drop, () -> register(type, drop));
    }

    /**
     * Remove a drop
     * @param type The type of drops
     * @return The removed drop
     */
    public static Optional<TokenDrop> remove(TokenDropType type) {
        Optional<TokenDrop[]> tokenDrops = find(type);
        return tokenDrops.map(drops -> {
            TokenDrop drop = drops[drops.length - 1];
            drops[drops.length - 1] = null;
            return drop;
        });
    }

    /**
     * Find a drop
     * @param type The type of drops
     * @return The drop
     */
    public static Optional<TokenDrop[]> find(TokenDropType type) {
        return Optional.ofNullable(TokenDropHandler.drops.get(type));
    }

}
