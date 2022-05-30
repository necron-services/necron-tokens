package dev.necron.tokens.bukkit.shop.item.requirement.type;

import dev.necron.tokens.bukkit.util.experience.ExperienceUtil;
import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class ExperienceShopRequirement implements ShopRequirement {

    private final int exp;

    @Override
    public boolean has(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return false;
        return ExperienceUtil.getExp(player) >= exp;
    }

    @Override
    public void execute(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        ExperienceUtil.changeExp(player, -exp);
    }

    @Override
    public String getName() {
        return "exp";
    }

}
