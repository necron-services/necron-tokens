package dev.necron.token.bukkit.drop.loader;

import dev.necron.token.common.drop.TokenDrop;
import dev.necron.token.common.drop.TokenDropType;
import dev.necron.token.common.drop.loader.TokenDropLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class BukkitTokenDropLoader implements TokenDropLoader {

    @Override
    public Map<TokenDropType, TokenDrop[]> load() throws Exception {
        return CompletableFuture.supplyAsync(() -> {

            Map<TokenDropType, TokenDrop[]> drops = new HashMap<>();

            return drops;

        }).get();
    }

}
