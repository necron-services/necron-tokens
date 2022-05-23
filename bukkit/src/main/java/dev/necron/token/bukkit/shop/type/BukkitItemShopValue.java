package dev.necron.token.bukkit.shop.type;

import dev.necron.token.common.shop.item.value.type.ItemShopValue;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BukkitItemShopValue extends ItemShopValue {

    public BukkitItemShopValue(Object value) {
        super(value);
    }

    @Override
    public <T> void execute(T player) {
        if (!(player instanceof Player)) throw new IllegalArgumentException("Player must be a Bukkit Player");
        if (!(value.getClass().equals(ItemStack.class))) throw new IllegalArgumentException("Value must be an ItemStack");
        Player p = (Player) player;
        Map<Integer, ItemStack> itemStackMap = p.getInventory().addItem((ItemStack) value);
        if (!itemStackMap.isEmpty()) p.getWorld().dropItem(p.getLocation(), itemStackMap.get(0));
    }

}

