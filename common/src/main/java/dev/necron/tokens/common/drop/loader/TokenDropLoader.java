package dev.necron.tokens.common.drop.loader;

import dev.necron.tokens.common.drop.TokenDrop;
import dev.necron.tokens.common.drop.TokenDropType;

import java.util.Map;

public interface TokenDropLoader {

    /**
     * Loads the drops
     * @return The drops
     */
    Map<TokenDropType, TokenDrop[]> load();

}
