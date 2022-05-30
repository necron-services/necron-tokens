package dev.necron.tokens.bukkit.menu.button.builder;

import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.button.MenuButton;
import dev.necron.tokens.bukkit.menu.handler.MenuHandler;
import dev.necron.tokens.bukkit.util.sound.XSound;
import dev.necron.tokens.common.shop.handler.ShopHandler;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MenuButtonBuilder {

    private final ConfigurationNode node;
    private final Menu menu;
    private final MenuButton button;

    public MenuButtonBuilder(ConfigurationNode node, Menu menu) {
        this.node = node;
        this.menu = menu;
        String name = node.getNode("name").getString();
        button = new MenuButton(name, node);
    }

    public static MenuButton of(ConfigurationNode node, Menu menu) {
        return new MenuButtonBuilder(node, menu).shop().shopItem().sound().item().clickEvent().build();
    }

    public MenuButtonBuilder shop() {
        if (node.getNode("shop").isEmpty()) return this;
        button.setShop(node.getNode("shop").getString());
        return this;
    }

    public MenuButtonBuilder shopItem() {
        if (node.getNode("shop-item").isEmpty()) return this;
        button.setShopItem(node.getNode("shop-item").getString());
        return this;
    }

    public MenuButtonBuilder item() {
        button.refresh(menu);
        return this;
    }

    public MenuButtonBuilder clickEvent() {
        button.setClickEvent(event -> {
            Player player = (Player) event.getWhoClicked();
            int itemSlot = event.getSlot();
            menu.findButton(itemSlot).ifPresent(button -> {
                if (menu.isShopSelector()) {
                    if (button.getShop() == null) return;
                    MenuHandler.findByShopName(button.getShop()).ifPresent(menu1 -> {
                        menu1.open(player);
                    });
                } else {
                    String shopName = menu.getShopName();
                    String shopItem = button.getShopItem();
                    if (shopName == null || shopItem == null) return;
                    ShopHandler.find(shopName).flatMap(shop -> shop.find(shopItem)).ifPresent(selectedShopItem -> {
                        if (selectedShopItem.hasRequirements(player.getUniqueId())) {
                            selectedShopItem.executeRequirements(player.getUniqueId());
                            selectedShopItem.execute(player.getUniqueId());
                            selectedShopItem.setStock(selectedShopItem.getStock() - 1);
                        }
                    });
                }
            });
        });
        return this;
    }

    public MenuButtonBuilder sound() {
        if (node.getNode("click-sound").isEmpty()) return this;
        Optional<XSound> optionalXSound = XSound.matchXSound(node.getNode("click-sound").getString());
        optionalXSound.ifPresent(sound -> button.setClickSound(sound.parseSound()));
        return this;
    }

    public MenuButton build() {
        return button;
    }

}
