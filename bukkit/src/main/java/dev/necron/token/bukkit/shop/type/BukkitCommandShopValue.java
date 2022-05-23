package dev.necron.token.bukkit.shop.type;

import dev.necron.token.common.shop.item.value.type.CommandShopValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitCommandShopValue extends CommandShopValue {

    public BukkitCommandShopValue(String command) {
        super(command);
    }

    @Override
    public void execute(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Bukkit.dispatchCommand(player, this.command.replace("%player%", player.getName()));
    }

}
