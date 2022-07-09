package dev.necron.tokens.bukkit.shop.item.requirement.type;

import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import dev.necron.tokens.common.player.TokenPlayer;
import dev.necron.tokens.common.player.TokenPlayerManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TokenShopRequirement implements ShopRequirement {

    private final long tokens;

    @Override
    public boolean has(UUID playerUUID) {
        Optional<TokenPlayer> optionalTokenPlayer = TokenPlayerManager.find(playerUUID);
        return optionalTokenPlayer.isPresent() && optionalTokenPlayer.get().getTokens() >= tokens;
    }

    @Override
    public void execute(UUID playerUUID) {
        Optional<TokenPlayer> optionalTokenPlayer = TokenPlayerManager.find(playerUUID);
        if (optionalTokenPlayer.isPresent()) {
            TokenPlayer tokenPlayer = optionalTokenPlayer.get();
            tokenPlayer.takeTokens(tokens);
        }
    }

    @Override
    public String getName() {
        return "token";
    }

}
