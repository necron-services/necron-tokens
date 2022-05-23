package dev.necron.token.bukkit.shop.type;

import dev.necron.token.common.shop.item.value.type.CommandShopValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitCommandShopValue extends CommandShopValue {

    public BukkitCommandShopValue(String command) {
        super(command);
    }

    @Override
    public <T> void execute(T player) {
        if (!(player instanceof Player)) throw new IllegalArgumentException("Player must be a Bukkit Player");
        Player p = (Player) player;
        Bukkit.dispatchCommand(p, this.command.replace("%player%", p.getName()));
    }

}
