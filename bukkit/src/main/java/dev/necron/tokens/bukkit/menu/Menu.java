package dev.necron.tokens.bukkit.menu;

import dev.necron.tokens.bukkit.menu.button.MenuButton;
import dev.necron.tokens.bukkit.util.ColorSerializer;
import dev.necron.tokens.bukkit.util.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class Menu {

    private final Map<Integer, MenuButton> menuButtonMap = new HashMap<>();

    private boolean shopSelector;
    @Nullable private String shopName;

    private Inventory inventory;
    @Nullable private Sound openSound, closeSound;

    private final ConfigurationNode node;
    private final String name;
    private final String title;
    private final int size;
    private final InventoryType type;

    public Menu(ConfigurationNode node, String name) {
        this.node = node;
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

    public void open(Player player) {
        menuButtonMap.forEach((slot, button) -> {
            if (button.refresh(this)) putButton(button);
        });
        player.openInventory(inventory);
    }

    public void fill(String item) {
        ItemBuilder builder = new ItemBuilder(item).setName(" ");
        ItemStack itemStack = builder.build();
        for (int i = 0; i < size; i++) {
            if (!menuButtonMap.containsKey(i)) inventory.setItem(i, itemStack);
        }
    }

    public Optional<MenuButton> findButton(int slot) {
        return Optional.ofNullable(menuButtonMap.get(slot));
    }

    public void putButton(MenuButton button) {
        int slot = button.getSlot();
        menuButtonMap.put(slot, button);
        inventory.setItem(slot, button.getItem());
    }

    public void removeButton(int slot) {
        menuButtonMap.remove(slot);
        inventory.setItem(slot, null);
    }

    public void clearButtons() {
        menuButtonMap.clear();
        inventory.clear();
    }

    public String getColoredTitle() {
        return ColorSerializer.serialize(title);
    }

}
