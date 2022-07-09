package dev.necron.tokens.bukkit.menu;

import dev.necron.tokens.bukkit.menu.button.MenuButton;
import dev.necron.tokens.bukkit.util.ColorSerializer;
import dev.necron.tokens.bukkit.util.item.ItemBuilder;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.key.ConfigKeys;
import lombok.Getter;
import lombok.Setter;
import ninja.leaping.configurate.ConfigurationNode;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter @Setter
public class Menu {

    private final Map<Integer, MenuButton[]> menuButtonMap = new HashMap<>();

    private boolean shopSelector;
    @Nullable private String shopName, permission;

    private Inventory inventory;
    @Nullable private Sound openSound, closeSound;

    private final String name;
    private final String title;
    private final int size;
    private final InventoryType type;

    public Menu(ConfigurationNode node, String name) {
        this.name = name;
        this.title = node.getNode("title").getString();
        if (!node.getNode("type").isEmpty()) {
            this.type = InventoryType.valueOf(node.getNode("type").getString().toUpperCase(Locale.ROOT));
            this.size = type.getDefaultSize();
        } else {
            this.type = InventoryType.CHEST;
            this.size = node.getNode("size").getInt();
        }
        this.inventory = Bukkit.createInventory(null, size, ColorSerializer.serialize(title));
    }

    public Menu(String name, String title, InventoryType inventoryType) {
        this.name = name;
        this.title = title;
        this.type = inventoryType;
        this.size = inventoryType.getDefaultSize();
        this.inventory = Bukkit.createInventory(null, size, ColorSerializer.serialize(title));
    }

    public Menu(String name, String title, int size) {
        this.name = name;
        this.title = title;
        this.size = size;
        this.type = InventoryType.CHEST;
        this.inventory = Bukkit.createInventory(null, size, ColorSerializer.serialize(title));
    }

    public void open(Player player) {
        if (permission != null && !player.hasPermission(permission)) {
            player.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PERMISSION.getValue()));
            return;
        }
        player.openInventory(inventory);
    }

    public void refresh() {
        menuButtonMap.keySet().forEach(this::selectButton);
    }

    public void fill(String item, int[] slots) {
        ItemBuilder builder = new ItemBuilder(item).setName(" ");
        ItemStack itemStack = builder.build();
        for (int i = 0; i < slots.length; i++) {
            int slot = slots[i];
            if (!menuButtonMap.containsKey(i)) inventory.setItem(slot, itemStack);
        }
    }

    public void selectButton(int slot) {
        if (!menuButtonMap.containsKey(slot)) return;
        MenuButton[] buttons = menuButtonMap.get(slot);
        if (buttons.length == 1) {
            MenuButton button = buttons[0];
            button.setSelected(true);
            button.refresh(this);
        }else {
            boolean selected = false;
            for (MenuButton button : buttons) {
                button.setSelected(false);
                if (!button.tryChance() || selected) continue;
                button.setSelected(true);
                button.refresh(this);
                selected = true;
            }
            if (!selected && buttons.length > 1) selectButton(slot);
        }
    }

    public Optional<MenuButton> findButton(int slot) {
        if (!menuButtonMap.containsKey(slot)) return Optional.empty();
        return Arrays.stream(menuButtonMap.get(slot))
                .filter(MenuButton::isSelected)
                .findFirst();
    }

    public void putButton(MenuButton button) {
        int slot = button.getSlot();
        if (!menuButtonMap.containsKey(slot)) {
            menuButtonMap.put(slot, new MenuButton[]{button});
            return;
        }
        MenuButton[] buttons = menuButtonMap.get(slot);
        menuButtonMap.put(slot, (MenuButton[]) ArrayUtils.add(buttons, button));
    }

    public void removeButton(int slot, String name) {
        Arrays.stream(menuButtonMap.get(slot))
                .filter(button -> button.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresent(button -> {
                    MenuButton[] buttons = menuButtonMap.get(slot);
                    menuButtonMap.put(slot, (MenuButton[]) ArrayUtils.removeElement(buttons, button));
                });
    }

    public void removeButtonBySlot(int slot) {
        menuButtonMap.remove(slot);
    }

    public void clearButtons() {
        menuButtonMap.clear();
        inventory.clear();
    }

    public String getColoredTitle() {
        return ColorSerializer.serialize(title);
    }

}
