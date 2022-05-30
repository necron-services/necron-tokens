package dev.necron.tokens.bukkit.shop.loader;

import dev.necron.tokens.bukkit.NecronTokensPlugin;
import dev.necron.tokens.bukkit.menu.button.builder.item.MenuItemBuilder;
import dev.necron.tokens.bukkit.shop.item.requirement.BukkitShopRequirementType;
import dev.necron.tokens.bukkit.shop.item.requirement.type.*;
import dev.necron.tokens.bukkit.shop.item.value.BukkitShopValueType;
import dev.necron.tokens.bukkit.shop.item.value.type.CommandShopValue;
import dev.necron.tokens.bukkit.shop.item.value.type.ItemShopValue;
import dev.necron.tokens.common.config.node.NodeLoader;
import dev.necron.tokens.common.shop.Shop;
import dev.necron.tokens.common.shop.item.ShopItem;
import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import dev.necron.tokens.common.shop.loader.ShopLoader;
import ninja.leaping.configurate.ConfigurationNode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BukkitShopLoader implements ShopLoader {

    @Override
    public Collection<Shop> load() {
        return CompletableFuture.supplyAsync(() -> {
            String dataFolder = NecronTokensPlugin.getInstance().getDataFolder().getAbsolutePath();
            File parentFile = new File(dataFolder + "/shop");
            File[] files = parentFile.listFiles();
            if (files == null || files.length == 0) {
                ClassLoader classLoader = NecronTokensPlugin.class.getClassLoader();
                try {
                    NodeLoader.loadNode(dataFolder + "/shop/example_shop_v1.yml", classLoader.getResourceAsStream("shop/example_shop_v1.yml"));
                    NodeLoader.loadNode(dataFolder + "/shop/example_shop_v2.yml", classLoader.getResourceAsStream("shop/example_shop_v2.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Collection<Shop> shops = new HashSet<>();
            Arrays.stream(files).forEach(file -> {
                try {
                    ConfigurationNode node = NodeLoader.loadNode(file).getNode("shop");
                    Shop shop = new Shop(node.getNode("name").getString(), node.getNode("refresh-time").getInt());
                    node = node.getNode("items");
                    for (ConfigurationNode child : node.getChildrenMap().values()) {
                        ShopItem shopItem = new ShopItem(child.getNode("name").getString(), child.getNode("stock").getInt());
                        for (String requirementNotParsed : (List<String>) child.getNode("requirements").getValue()) {
                            String[] requirement = requirementNotParsed.split(": ");
                            if (requirement.length != 2) continue;
                            BukkitShopRequirementType type = BukkitShopRequirementType.of(requirement[0]);
                            if (type == null) {
                                System.out.println("Unknown requirement type: " + requirement[0]);
                                continue;
                            }
                            ShopRequirement shopRequirement = type.create(requirement[1]);
                            shopItem.putShopRequirement(shopRequirement);
                        }
                        ConfigurationNode values = child.getNode("values");
                        for (ConfigurationNode value : values.getChildrenMap().values()) {
                            String name = String.valueOf(value.getKey());
                            if (value.getNode("type").isEmpty()) {
                                System.out.println("Value not found: " + name);
                                continue;
                            }
                            BukkitShopValueType type = BukkitShopValueType.of(value.getNode("type").getString());
                            if (type == null) {
                                System.out.println("Unknown value type: " + name);
                                continue;
                            }
                            switch (type) {
                                case COMMAND:
                                    shopItem.putShopValue(name, new CommandShopValue(value.getNode("command").getString()));
                                    break;
                                case ITEM:
                                    MenuItemBuilder menuItemBuilder = MenuItemBuilder.of(value.getNode("item"));
                                    ItemShopValue itemShopValue = new ItemShopValue(menuItemBuilder.build());
                                    shopItem.putShopValue(name, itemShopValue);
                                    break;
                            }
                        }
                        shop.put(shopItem);
                    }
                    shops.add(shop);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return shops;
        }).join();
    }

}
