package dev.necron.tokens.bukkit.shop.item.value.type;

import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.MessageType;
import dev.necron.tokens.bukkit.message.category.MessageCategories;
import dev.necron.tokens.bukkit.message.category.type.ShopMessages;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.shop.item.ShopItem;
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
    private final boolean applyAmountToStock;

    @Override
    public void execute(ShopItem shopItem, UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        Map<Integer, ItemStack> itemStackMap = player.getInventory().addItem(value.clone());
        if (!itemStackMap.isEmpty()) {
            player.getWorld().dropItem(player.getLocation(), itemStackMap.get(0));

            ShopMessages messages = (ShopMessages) MessageCategories.SHOP_MESSAGES.getInstance();
            Message message = messages.INVENTORY_FULL;
            message.execute(player, () -> LanguageUtil.replace(message.getValue()));

            if (applyAmountToStock) shopItem.setStock(shopItem.getStock() - (itemStackMap.get(0).getAmount() - 1));
        }
    }

}

