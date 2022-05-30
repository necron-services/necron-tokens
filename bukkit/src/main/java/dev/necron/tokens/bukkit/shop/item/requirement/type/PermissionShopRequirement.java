package dev.necron.tokens.bukkit.shop.item.requirement.type;

import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class PermissionShopRequirement implements ShopRequirement {

    private final String permission;

    @Override
    public boolean has(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        return player != null && player.hasPermission(permission);
    }

    @Override
    public void execute(UUID playerUUID) {}

    @Override
    public String getName() {
        return "perm";
    }

}
