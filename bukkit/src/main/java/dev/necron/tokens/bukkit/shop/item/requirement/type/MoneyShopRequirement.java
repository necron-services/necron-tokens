package dev.necron.tokens.bukkit.shop.item.requirement.type;

import dev.necron.tokens.bukkit.hook.VaultHook;
import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class MoneyShopRequirement implements ShopRequirement {

    private final long cost;

    @Override
    public boolean has(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return false;
        return VaultHook.getEconomy().has(player, cost);
    }

    @Override
    public void execute(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        VaultHook.getEconomy().withdrawPlayer(player, cost);
    }

    @Override
    public String getName() {
        return "cost";
    }
}
