package dev.necron.tokens.bukkit.shop.item.value.type;

import dev.necron.tokens.common.shop.item.value.ShopValue;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ItemShopValue implements ShopValue {

    private final ItemStack value;

    @Override
    public void execute(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Map<Integer, ItemStack> itemStackMap = player.getInventory().addItem(value.clone());
        if (!itemStackMap.isEmpty()) player.getWorld().dropItem(player.getLocation(), itemStackMap.get(0));
    }

}

