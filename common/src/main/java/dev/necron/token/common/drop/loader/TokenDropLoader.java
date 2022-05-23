package dev.necron.token.common.drop.loader;

import dev.necron.token.common.drop.TokenDrop;
import dev.necron.token.common.drop.TokenDropType;

import java.util.Map;

public interface TokenDropLoader {

    /**
     * Loads the drops
     * @return The drops
     */
    Map<TokenDropType, TokenDrop[]> load() throws Exception;

}
