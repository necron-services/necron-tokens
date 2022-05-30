package dev.necron.tokens.bukkit.util.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.necron.tokens.bukkit.util.ColorSerializer;
import dev.necron.tokens.bukkit.util.item.material.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    final private ItemStack itemStack;
    final private ItemMeta itemMeta;

    public ItemBuilder(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(final String material) {
        if (material == null) throw new NullPointerException("Material is not exists!!");
        final Optional<XMaterial> optionalXMaterial = XMaterial.matchXMaterial(material);
        if (!optionalXMaterial.isPresent()) throw new NullPointerException("Material is not exists!");
        this.itemStack = optionalXMaterial.get().parseItem();
        this.itemMeta = itemStack.getItemMeta();
    }

    @Nullable
    public String getName() {
        return itemMeta.getDisplayName();
    }

    public ItemBuilder setName(final String name) {
        if (name == null) return this;
        itemMeta.setDisplayName(ColorSerializer.serialize(name));
        return this;
    }

    public ItemBuilder clearName() {
        itemMeta.setDisplayName(null);
        return this;
    }

    public boolean hasName() {
        return itemMeta.hasDisplayName();
    }

    @Nullable
    public List<String> getLoreList() {
        return itemMeta.getLore();
    }

    public ItemBuilder setLore(final List<String> loreList) {
        if (loreList == null) return this;
        if (!loreList.isEmpty()) loreList.replaceAll(lore -> ColorSerializer.serialize(lore));
        itemMeta.setLore(loreList);
        return this;
    }

    public ItemBuilder addLore(final String lore) {
        if (lore == null) return this;
        final List<String> loreList = (hasLore() ? getLoreList() : new ArrayList<>());
        loreList.add(ColorSerializer.serialize(lore));
        setLore(loreList);
        return this;
    }

    public ItemBuilder removeLore(final String lore, final boolean chatColor) {
        if (lore == null || !hasLore()) return this;
        final List<String> loreList = getLoreList();
        loreList.remove(chatColor ? ColorSerializer.serialize(lore) : lore);
        setLore(loreList);
        return this;
    }

    public ItemBuilder clearLore() {
        itemMeta.setLore(new ArrayList<>());
        return this;
    }

    public boolean containsLore(final String lore, final boolean chatColor) {
        if (lore == null || !hasLore()) return false;
        return getLoreList().contains(chatColor ? ColorSerializer.serialize(lore) : lore);
    }

    public boolean hasLore() {
        return itemMeta.hasLore();
    }

    @Nullable
    public Map<Enchantment, Integer> getEnchants() {
        return itemMeta.getEnchants();
    }

    public ItemBuilder setEnchants(final Map<Enchantment, Integer> enchants, final boolean clear) {
        if (enchants == null) return this;
        if (clear) {
            if (hasEnchants());
        }
        return this;
    }

    public ItemBuilder clearEnchants() {
        if (!hasEnchants()) return this;
        final List<Enchantment> enchantments = new ArrayList<>();
        enchantments.addAll(getEnchants().keySet());
        enchantments.forEach(this::removeEnchant);
        return this;
    }

    public ItemBuilder putEnchant(final Enchantment enchantment, final int level) {
        if (enchantment == null) return this;
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(final Enchantment enchantment) {
        if (enchantment == null) return this;
        itemMeta.removeEnchant(enchantment);
        return this;
    }

    public int getEnchantLevel(final Enchantment enchantment) {
        return itemMeta.getEnchantLevel(enchantment);
    }

    public boolean hasEnchant(final Enchantment enchantment) {
        if (!hasEnchants()) return false;
        return itemMeta.hasEnchant(enchantment);
    }

    public boolean hasEnchants() {
        return itemMeta.hasEnchants();
    }

    public ItemBuilder glow() {
        if (hasItemFlag(ItemFlag.HIDE_ENCHANTS)) return this;
        if (!hasEnchants()) putEnchant(Enchantment.DURABILITY, 1);
        addItemFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setSkullOwner(final String player) {
        try{
            SkullMeta im = (SkullMeta) itemMeta;
            im.setOwner(player);
        }catch(ClassCastException expected){}
        return this;
    }

    public ItemBuilder setHeadProfile(final String texture) {
        if (texture == null) return this;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            Field profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Nullable
    public Collection<ItemFlag> getItemFlags() {
        return itemMeta.getItemFlags();
    }

    public boolean hasItemFlags() {
        return getItemFlags() != null;
    }

    public ItemBuilder setItemFlags(final Collection<ItemFlag> itemFlags, final boolean clear) {
        if (itemFlags == null) return this;
        if (clear && hasItemFlags()) clearItemFlags();
        itemFlags.forEach(this::addItemFlag);
        return this;
    }

    public ItemBuilder clearItemFlags() {
        final Collection<ItemFlag> itemFlags = getItemFlags();
        if (itemFlags == null) return this;
        itemFlags.forEach(itemFlag -> itemMeta.removeItemFlags(itemFlag));
        return this;
    }

    public boolean hasItemFlag(final ItemFlag itemFlag) {
        if (itemFlag == null) return false;
        return itemMeta.hasItemFlag(itemFlag);
    }

    public ItemBuilder addItemFlag(final ItemFlag itemFlag) {
        if (itemFlag == null) return this;
        itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder removeItemFlag(final ItemFlag itemFlag) {
        if (itemFlag == null || !hasItemFlag(itemFlag)) return this;
        itemMeta.removeItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder setUnbreakable(final boolean var) {
        itemMeta.setUnbreakable(var);
        return this;
    }

    public ItemBuilder setAmount(final int var) {
        if (var <= 0) return this;
        itemStack.setAmount(var);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
