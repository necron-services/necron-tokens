package dev.necron.tokens.bukkit.menu.button;

import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.button.builder.item.MenuItemBuilder;
import dev.necron.tokens.common.shop.handler.ShopHandler;
import dev.necron.tokens.common.util.TimeParser;
import lombok.Getter;
import lombok.Setter;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@Getter @Setter
public class MenuButton {

    private ItemStack item;
    private int slot;

    @Nullable private Sound clickSound;
    @Nullable private String shop, shopItem;
    private long lastRefresh;

    private Consumer<InventoryClickEvent> clickEvent;

    private final String name;
    private final ConfigurationNode node;

    public MenuButton(String name, ConfigurationNode node) {
        this.name = name;
        this.node = node;
        this.slot = node.getNode("slot").getInt();
    }

    public boolean refresh(Menu menu) {
        if (lastRefresh != 0 && System.currentTimeMillis() - lastRefresh < 1000) return false;
        if (menu.isShopSelector() && shop != null) {
            ShopHandler.find(shop).ifPresent(shop -> {
                MenuItemBuilder builder = MenuItemBuilder.of(node,
                        new String[]{"%refresh-time%"},
                        new String[]{TimeParser.parse(shop.getRefreshTimeLeft())});
                item = builder.build();
            });
        } else if (shopItem != null) {
            ShopHandler.find(menu.getShopName()).flatMap(shop -> shop.find(shopItem)).ifPresent(selectedShopItem -> {
                MenuItemBuilder builder = MenuItemBuilder.of(node,
                        new String[]{"%stock%", "%max-stock%"},
                        new String[]{String.valueOf(selectedShopItem.getStock()), String.valueOf(selectedShopItem.getMaxStock())});
                item = builder.build();
            });
        } else {
            MenuItemBuilder builder = MenuItemBuilder.of(node);
            item = builder.build();
        }
        lastRefresh = System.currentTimeMillis();
        return true;
    }

}
