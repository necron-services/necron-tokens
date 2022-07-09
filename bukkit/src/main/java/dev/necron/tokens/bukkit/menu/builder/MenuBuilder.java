package dev.necron.tokens.bukkit.menu.builder;

import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.builder.button.MenuButtonBuilder;
import dev.necron.tokens.bukkit.menu.button.MenuButton;
import dev.necron.tokens.bukkit.util.sound.XSound;
import dev.necron.tokens.common.shop.ShopManager;
import ninja.leaping.configurate.ConfigurationNode;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class MenuBuilder {

    private final ConfigurationNode node;
    private final Menu menu;

    public MenuBuilder(ConfigurationNode node, String name) {
        this.node = node;
        name = parseName(name);

        if (!node.getNode("type").isEmpty()) {
            InventoryType type = InventoryType.valueOf(node.getNode("type").getString().toUpperCase(Locale.ROOT));
            String title = node.getNode("title").getString();
            menu = new Menu(name, title, type);
        } else {
            String title = node.getNode("title").getString();
            int size = node.getNode("size").getInt();
            menu = new Menu(name, title, size);
        }
    }

    public static MenuBuilder of(ConfigurationNode node, String name) {
        return new MenuBuilder(node, name);
    }

    public MenuBuilder permission() {
        if (!node.getNode("permission").isEmpty()) {
            menu.setPermission(node.getNode("permission").getString());
        }
        return this;
    }

    public MenuBuilder shop() {
        menu.setShopSelector(node.getNode("shop-selector-menu").getBoolean());
        if (!node.getNode("shop").isEmpty()) {
            String shopName = node.getNode("shop").getString();
            ShopManager.find(shopName).ifPresent(shop -> {
                menu.setShopName(shopName);
                shop.onRefresh(menu::refresh);
            });
        }
        return this;
    }

    public MenuBuilder items() {
        for (Object key : node.getNode("items").getChildrenMap().keySet()) {
            ConfigurationNode itemNode = node.getNode("items").getNode(key);
            MenuButton menuButton = MenuButtonBuilder.of(itemNode, menu);
            menu.putButton(menuButton);
        }
        if (!node.getNode("filler").isEmpty()) {
            ConfigurationNode node = this.node.getNode("filler");
            String material = node.getNode("material").getString();

            int[] slot = null;
            Object slotObject = node.getNode("slot").getValue();
            if (slotObject instanceof Integer) {
                slot = new int[]{(int) slotObject};
            } else if (slotObject instanceof List) {
                List<Integer> slots = (List<Integer>) slotObject;
                int[] slotsArray = slots.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();
                slot = slotsArray;
            }else if (slotObject instanceof String) {
                String slotString = (String) slotObject;
                String[] split = slotString.split("-");
                int minSlot = Integer.parseInt(split[0]);
                int maxSlot = Integer.parseInt(split[1]);
                slot = new int[maxSlot - minSlot + 1];
                for (int i = minSlot; i <= maxSlot; i++) {
                    slot[i - minSlot] = i;
                }
            }
            menu.fill(material, slot);
        }
        return this;
    }

    public MenuBuilder openSound() {
        if (!node.getNode("open-sound").isEmpty()) {
            Optional<XSound> optionalXSound = XSound.matchXSound(node.getNode("open-sound").getString());
            optionalXSound.ifPresent(sound -> menu.setOpenSound(sound.parseSound()));
        }
        return this;
    }

    public MenuBuilder closeSound() {
        if (!node.getNode("close-sound").isEmpty()) {
            Optional<XSound> optionalXSound = XSound.matchXSound(node.getNode("close-sound").getString());
            optionalXSound.ifPresent(sound -> menu.setCloseSound(sound.parseSound()));
        }
        return this;
    }

    public Menu build() {
        return menu;
    }

    public Menu autoBuild() {
        return this.permission()
                .shop()
                .items()
                .openSound()
                .closeSound()
                .build();
    }

    private String parseName(String name) {
        return name.toUpperCase(Locale.ROOT)
                .replace(".yml", "")
                .replace("_", "")
                .replace("menu", "");
    }

}
