package dev.necron.tokens.bukkit.menu.button;

import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.builder.button.item.MenuItemBuilder;
import dev.necron.tokens.common.shop.ShopManager;
import dev.necron.tokens.common.util.ChanceUtil;
import dev.necron.tokens.common.util.TimeParser;
import lombok.Getter;
import lombok.Setter;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@Getter
@Setter
public class MenuButton {

    private ItemStack item;
    private int slot;

    @Nullable
    private Sound clickSound;
    @Nullable
    private String shop, shopItem;
    private boolean selected;
    private double chance;

    private Consumer<InventoryClickEvent> clickEvent;

    private final String name;
    private final ConfigurationNode node;

    public MenuButton(String name, ConfigurationNode node) {
        this.name = name;
        this.node = node;
        this.slot = node.getNode("slot").getInt();
    }

    public boolean refresh(Menu menu) {
        if (!selected) return false;
        if (menu.isShopSelector() && shop != null) {
            ShopManager.find(shop).ifPresent(shop -> {
                MenuItemBuilder builder = MenuItemBuilder.of(node,
                        new String[]{"%refresh-time%"},
                        new String[]{TimeParser.parse(shop.getRefreshTimeLeft())});
                item = builder.build();
            });
        } else if (shopItem != null) {
            ShopManager.find(menu.getShopName()).ifPresent(shop -> {
                shop.find(shopItem).ifPresent(selectedShopItem -> {
                    MenuItemBuilder builder = MenuItemBuilder.of(node,
                            new String[]{
                                    "%stock%",
                                    "%max-stock%",
                                    "%refresh-time%"
                            },
                            new String[]{
                                    String.valueOf(selectedShopItem.getStock()),
                                    String.valueOf(selectedShopItem.getMaxStock()),
                                    TimeParser.parse(shop.getRefreshTimeLeft())
                            });
                    item = builder.build();
                });
            });
        } else {
            MenuItemBuilder builder = MenuItemBuilder.of(node);
            item = builder.build();
        }
        return true;
    }

    public boolean tryChance() {
        return ChanceUtil.tryChance(chance);
    }

}
