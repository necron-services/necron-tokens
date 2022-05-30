package dev.necron.tokens.bukkit.shop.item.requirement.type;

import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import dev.necron.tokens.common.token.TokenPlayer;
import dev.necron.tokens.common.token.TokenPlayerHandler;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TokenShopRequirement implements ShopRequirement {

    private final long tokens;

    @Override
    public boolean has(UUID playerUUID) {
        Optional<TokenPlayer> optionalTokenPlayer = TokenPlayerHandler.find(playerUUID);
        return optionalTokenPlayer.isPresent() && optionalTokenPlayer.get().getTokens() >= tokens;
    }

    @Override
    public void execute(UUID playerUUID) {
        Optional<TokenPlayer> optionalTokenPlayer = TokenPlayerHandler.find(playerUUID);
        if (optionalTokenPlayer.isPresent()) {
            TokenPlayer tokenPlayer = optionalTokenPlayer.get();
            tokenPlayer.removeTokens(tokens);
        }
    }

    @Override
    public String getName() {
        return "token";
    }

}
