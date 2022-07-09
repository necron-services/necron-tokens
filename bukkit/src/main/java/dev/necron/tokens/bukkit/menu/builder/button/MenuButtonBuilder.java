package dev.necron.tokens.bukkit.menu.builder.button;

import dev.necron.tokens.bukkit.event.shop.ShopTransactionEvent;
import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.button.MenuButton;
import dev.necron.tokens.bukkit.menu.MenuManager;
import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.MessageType;
import dev.necron.tokens.bukkit.message.category.MessageCategories;
import dev.necron.tokens.bukkit.message.category.type.ShopMessages;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.bukkit.util.sound.XSound;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.shop.ShopManager;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.Bukkit;
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
        return new MenuButtonBuilder(node, menu).chance().shop().shopItem().sound().item().clickEvent().build();
    }

    public MenuButtonBuilder chance() {
        if (node.getNode("preview-chance").isEmpty()) return this;
        button.setChance(node.getNode("preview-chance").getDouble());
        return this;
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
                    MenuManager.findMenuByShopName(button.getShop()).ifPresent(selectedMenu -> {
                        selectedMenu.open(player);
                    });
                } else {
                    String shopName = menu.getShopName();
                    String shopItem = button.getShopItem();
                    if (shopName == null || shopItem == null) return;
                    ShopManager.find(shopName).ifPresent(shop -> shop.find(shopItem).ifPresent(selectedShopItem -> {
                        boolean soundsEnabled = ConfigKeys.Settings.SHOP_SOUNDS_ENABLED.getValue();
                        if (selectedShopItem.hasStock() && selectedShopItem.hasRequirements(player.getUniqueId())) {
                            ShopTransactionEvent shopTransactionEvent = new ShopTransactionEvent(
                                    player,
                                    shop,
                                    selectedShopItem,
                                    false
                            );
                            Bukkit.getPluginManager().callEvent(shopTransactionEvent);
                            if (shopTransactionEvent.isCancelled()) return;

                            selectedShopItem.executeRequirements(player.getUniqueId());
                            selectedShopItem.execute(player.getUniqueId());
                            selectedShopItem.setStock(selectedShopItem.getStock() - 1);

                            ShopMessages messages = (ShopMessages) MessageCategories.SHOP_MESSAGES.getInstance();
                            Message message = messages.BOUGHT_SHOP_ITEM;
                            message.execute(player, () -> LanguageUtil.replace(message.getValue(),
                                    new String[]{"%item%"},
                                    new String[]{selectedShopItem.getName()}));

                            if (soundsEnabled) {
                                XSound.matchXSound(ConfigKeys.Settings.SHOP_BUY_SOUND.getValue())
                                        .ifPresent(xSound -> xSound.play(player));
                            }
                            if (ConfigKeys.Settings.SHOP_CLOSE_MENU_ON_BUY.getValue()) player.closeInventory();
                        } else {
                            ShopMessages messages = (ShopMessages) MessageCategories.SHOP_MESSAGES.getInstance();
                            Message message = messages.NOT_ENOUGH_THINGS;
                            message.execute(player, () -> LanguageUtil.replace(message.getValue()));

                            if (soundsEnabled) {
                                XSound.matchXSound(ConfigKeys.Settings.SHOP_CANNOT_BUY_SOUND.getValue())
                                        .ifPresent(xSound -> xSound.play(player));
                            }
                        }
                    }));
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
