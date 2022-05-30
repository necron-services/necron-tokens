package dev.necron.tokens.bukkit.shop.item.requirement.type;

import dev.necron.tokens.bukkit.hook.VaultHook;
import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class GroupShopRequirement implements ShopRequirement {

    private final String group;

    @Override
    public boolean has(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return false;
        return VaultHook.getPermission().has(player, group);
    }

    @Override
    public void execute(UUID playerUUID) {

    }

    @Override
    public String getName() {
        return "group";
    }
}
