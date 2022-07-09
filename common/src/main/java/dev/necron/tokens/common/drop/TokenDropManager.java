package dev.necron.tokens.common.drop;

import dev.necron.tokens.common.drop.loader.TokenDropLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TokenDropManager {

    private static final Map<TokenDropType, TokenDrop[]> drops = new HashMap<>();

    /**
     * Initialize the drop handler
     *
     * @param dropLoader The drop loader
     */
    public static void init(TokenDropLoader dropLoader) {
        drops.clear();
        TokenDropManager.drops.putAll(dropLoader.load());
    }

    /**
     * Execute the drops to the player with the drop type
     * @param type The drop type
     * @param playerUUID The player UUID
     * @param executableObject The executable object
     */
    public static void execute(TokenDropType type, UUID playerUUID, Object executableObject) {
        Optional.ofNullable(TokenDropManager.drops.get(type)).ifPresent(drops -> {
            for (TokenDrop drop : drops) drop.execute(playerUUID, executableObject);
        });
    }

    /**
     * Register a drops
     *
     * @param type  The type of drops
     * @param drops The drops
     */
    public static void register(TokenDropType type, TokenDrop... drops) {
        TokenDropManager.drops.put(type, drops);
    }

    /**
     * Unregister a drops
     *
     * @param type The type of drops
     */
    public static void unregister(TokenDropType type) {
        TokenDropManager.drops.remove(type);
    }

    /**
     * Put a drop
     *
     * @param type The type of drops
     * @param drop The drop
     */
    public static void put(TokenDropType type, TokenDrop drop) {
        Optional<TokenDrop[]> tokenDrops = find(type);
        if (tokenDrops.isPresent()) {
            TokenDrop[] drops = tokenDrops.get();
            TokenDrop[] newDrops = new TokenDrop[drops.length + 1];
            System.arraycopy(drops, 0, newDrops, 0, drops.length);
            newDrops[drops.length] = drop;
            TokenDropManager.drops.put(type, newDrops);
        } else TokenDropManager.drops.put(type, new TokenDrop[]{drop});
    }

    /**
     * Remove a drop
     *
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
     *
     * @param type The type of drops
     * @return The drop
     */
    public static Optional<TokenDrop[]> find(TokenDropType type) {
        return Optional.ofNullable(TokenDropManager.drops.get(type));
    }

}
