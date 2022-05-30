package dev.necron.tokens.bukkit.menu.loader;

import dev.necron.tokens.bukkit.NecronTokensPlugin;
import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.button.MenuButton;
import dev.necron.tokens.bukkit.menu.button.builder.MenuButtonBuilder;
import dev.necron.tokens.bukkit.util.sound.XSound;
import dev.necron.tokens.common.config.node.NodeLoader;
import ninja.leaping.configurate.ConfigurationNode;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MenuLoader {

    public static Collection<Menu> load()  {
        return CompletableFuture.supplyAsync(() -> {
            String dataFolder = NecronTokensPlugin.getInstance().getDataFolder().getAbsolutePath();
            File parentFile = new File(dataFolder + "/menu");
            File[] files = parentFile.listFiles();
            if (files == null || files.length == 0) {
                ClassLoader classLoader = NecronTokensPlugin.class.getClassLoader();
                try {
                    NodeLoader.load(dataFolder + "/menu/shop_selector_menu.yml", classLoader.getResourceAsStream("menu/shop_selector_menu.yml"));
                    NodeLoader.load(dataFolder + "/menu/example_shop_v1_menu.yml", classLoader.getResourceAsStream("menu/example_shop_v1_menu.yml"));
                    NodeLoader.load(dataFolder + "/menu/example_shop_v2_menu.yml", classLoader.getResourceAsStream("menu/example_shop_v2_menu.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Collection<Menu> menus = new HashSet<>();
            Arrays.asList(files).forEach(file -> {
                try {
                    ConfigurationNode node = NodeLoader.load(file).getNode("menu");
                    Menu menu = new Menu(node, file.getName()
                            .toUpperCase(Locale.ROOT)
                            .replace(".yml", "")
                            .replace("_", "")
                            .replace("menu", ""));
                    if (!node.getNode("open-sound").isEmpty()) {
                        Optional<XSound> optionalXSound = XSound.matchXSound(node.getNode("open-sound").getString());
                        optionalXSound.ifPresent(sound -> menu.setOpenSound(sound.parseSound()));
                    }
                    if (!node.getNode("close-sound").isEmpty()) {
                        Optional<XSound> optionalXSound = XSound.matchXSound(node.getNode("close-sound").getString());
                        optionalXSound.ifPresent(sound -> menu.setCloseSound(sound.parseSound()));
                    }
                    menu.setShopSelector(node.getNode("shop-selector-menu").getBoolean());
                    if (!node.getNode("shop").isEmpty()) menu.setShopName(node.getNode("shop").getString());
                    for (Object key : node.getNode("items").getChildrenMap().keySet()) {
                        ConfigurationNode itemNode = node.getNode("items").getNode(key);
                        MenuButton menuButton = MenuButtonBuilder.of(itemNode, menu);
                        menu.putButton(menuButton);
                    }
                    if (!node.getNode("fill").isEmpty()) menu.fill(node.getNode("fill").getString());
                    menus.add(menu);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return menus;
        }).join();
    }
}
