package dev.necron.tokens.bukkit.shop.loader;

import dev.necron.tokens.bukkit.NecronTokensPlugin;
import dev.necron.tokens.bukkit.menu.builder.button.item.MenuItemBuilder;
import dev.necron.tokens.bukkit.shop.item.requirement.BukkitShopRequirementType;
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

    private static final String DATA_FOLDER = "plugins/NecronTokens";

    @Override
    public Collection<Shop> load() {
        return CompletableFuture.supplyAsync(() -> {
            File parentFile = new File(DATA_FOLDER + "/shop");
            File[] files = parentFile.listFiles();
            if (files == null || files.length == 0) {
                ClassLoader classLoader = NecronTokensPlugin.class.getClassLoader();
                try {
                    NodeLoader.load(DATA_FOLDER + "/shop/example_shop_v1.yml", classLoader.getResourceAsStream("shop/example_shop_v1.yml"));
                    NodeLoader.load(DATA_FOLDER + "/shop/example_shop_v2.yml", classLoader.getResourceAsStream("shop/example_shop_v2.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Collection<Shop> shops = new HashSet<>();
            Arrays.asList(files).forEach(file -> {
                try {
                    ConfigurationNode node = NodeLoader.load(file).getNode("shop");
                    Shop shop = new Shop(node.getNode("name").getString(), node.getNode("refresh-time").getInt());
                    node = node.getNode("items");
                    for (ConfigurationNode child : node.getChildrenMap().values()) {
                        ShopItem shopItem = findShopItem(child);
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

    private static ShopItem findShopItem(ConfigurationNode node) {
        ShopItem shopItem = new ShopItem(node.getNode("name").getString(), node.getNode("stock").getInt());
        for (String requirementNotParsed : (List<String>) node.getNode("requirements").getValue()) {
            String[] requirement = requirementNotParsed.split(": ");
            if (requirement.length != 2) continue;
            BukkitShopRequirementType type = BukkitShopRequirementType.of(requirement[0]);
            if (type == null) {
                System.out.println("Unknown requirement type: " + requirement[0]);
                continue;
            }
            ShopRequirement shopRequirement = type.parse(requirement[1]);
            shopItem.putRequirement(shopRequirement);
        }
        ConfigurationNode values = node.getNode("values");
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
                    String command = value.getNode("command").getString();
                    shopItem.putValue(name, new CommandShopValue(command));
                    break;
                case ITEM:
                    MenuItemBuilder menuItemBuilder = MenuItemBuilder.of(value.getNode("item"));
                    ItemShopValue itemShopValue = new ItemShopValue(
                            menuItemBuilder.build(),
                            value.getNode("apply-amount-to-stock").getBoolean()
                    );
                    shopItem.putValue(name, itemShopValue);
                    break;
            }
        }
        return shopItem;
    }

}
