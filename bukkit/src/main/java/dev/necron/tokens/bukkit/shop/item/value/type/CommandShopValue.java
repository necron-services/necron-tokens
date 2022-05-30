package dev.necron.tokens.bukkit.shop.item.value.type;

import dev.necron.tokens.common.shop.item.value.ShopValue;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class CommandShopValue implements ShopValue {

    private final String command;

    @Override
    public void execute(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
    }

}
