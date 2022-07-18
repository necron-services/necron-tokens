package dev.necron.tokens.bukkit.menu.builder.button.item;

import dev.necron.tokens.bukkit.util.item.ItemBuilder;
import dev.necron.tokens.bukkit.util.item.material.XMaterial;
import ninja.leaping.configurate.ConfigurationNode;
import org.apache.commons.lang.StringUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuItemBuilder {

    private final ConfigurationNode node;
    private final ItemBuilder itemBuilder;

    private MenuItemBuilder(ConfigurationNode node) {
        this.node = node;
        String material = node.getNode("material").getString();
        if (material == null) throw new IllegalArgumentException("Material cannot be null");
        if (material.startsWith("HEAD: ")) itemBuilder = new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem());
        else this.itemBuilder = new ItemBuilder(material);
    }

    public static MenuItemBuilder of(ConfigurationNode node, String[] targets, String[] replacements) {
        return new MenuItemBuilder(node)
                .amount()
                .name(targets, replacements)
                .lore(targets, replacements)
                .enchantments()
                .glow()
                .head();
    }

    public static MenuItemBuilder of(ConfigurationNode node) {
        return new MenuItemBuilder(node)
                .amount()
                .name()
                .lore()
                .enchantments()
                .glow()
                .head();
    }

    public MenuItemBuilder amount() {
        if ((node.getNode("amount").isEmpty())) return this;
        itemBuilder.setAmount(node.getNode("amount").getInt());
        return this;
    }

    public MenuItemBuilder name() {
        if(node.getNode("name").isEmpty()) return this;
        itemBuilder.setName(node.getNode("name").getString());
        return this;
    }

    public MenuItemBuilder name(String[] targets, String[] replacements) {
        if(node.getNode("name").isEmpty()) return this;
        itemBuilder.setName(StringUtils.replaceEach(node.getNode("name").getString(), targets, replacements));
        return this;
    }

    public MenuItemBuilder lore() {
        if (node.getNode("lore").isEmpty()) return this;
        itemBuilder.setLore((List<String>) node.getNode("lore").getValue());
        return this;
    }

    public MenuItemBuilder lore(String[] targets, String[] replacements) {
        if (node.getNode("lore").isEmpty()) return this;
        List<String> loreList = (List<String>) node.getNode("lore").getValue();
        loreList.replaceAll(lore -> StringUtils.replaceEach(lore, targets, replacements));
        itemBuilder.setLore(loreList);
        return this;
    }


    public MenuItemBuilder enchantments() {
        if (node.getNode("enchant").isEmpty()) return this;
        List<String> enchantments = (List<String>) node.getNode("enchant").getValue();
        enchantments.forEach(enchantment -> {
            String[] split = enchantment.split(": ");
            itemBuilder.putEnchant(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
        });
        return this;
    }

    public MenuItemBuilder glow() {
        if (node.getNode("glow").getBoolean()) {
            if (!itemBuilder.hasEnchants()) itemBuilder.putEnchant(Enchantment.DURABILITY, 1);
            itemBuilder.addItemFlag(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public MenuItemBuilder head() {
        if (node.getNode("head").isEmpty()) return this;
        String material = node.getNode("head").getString();
        if (!material.startsWith("HEAD: ")) return this;
        String[] split = material.split(": ");
        itemBuilder.setHeadProfile(split[1]);
        return this;
    }

    public MenuItemBuilder head(String playerName) {
        if (node.getNode("head").isEmpty()) return this;
        String material = node.getNode("head").getString();
        if (!material.startsWith("HEAD: ")) return this;
        String[] split = material.split(" ");
        if (split[1].equals("%player%")) itemBuilder.setSkullOwner(playerName);
        else itemBuilder.setHeadProfile(split[1]);
        return this;
    }

    public MenuItemBuilder apply(Inventory inventory) {
        inventory.addItem(itemBuilder.build());
        return this;
    }

    public MenuItemBuilder apply(Inventory inventory, int... slot) {
        for (int i : slot) inventory.setItem(i, itemBuilder.build());
        return this;
    }

    public ItemStack build() {
        return itemBuilder.build();
    }

}
