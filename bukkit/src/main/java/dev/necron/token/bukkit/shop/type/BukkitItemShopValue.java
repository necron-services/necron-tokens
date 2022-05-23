package dev.necron.token.bukkit.shop.type;

import dev.necron.token.common.shop.item.value.type.ItemShopValue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class BukkitItemShopValue extends ItemShopValue {

    public BukkitItemShopValue(Object value) {
        super(value);
    }

    @Override
    public void execute(UUID playerUUID) {
        if (!(value.getClass().equals(ItemStack.class))) throw new IllegalArgumentException("Value must be an ItemStack");
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Map<Integer, ItemStack> itemStackMap = player.getInventory().addItem((ItemStack) value);
        if (!itemStackMap.isEmpty()) player.getWorld().dropItem(player.getLocation(), itemStackMap.get(0));
    }

}

