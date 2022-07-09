package dev.necron.tokens.bukkit.menu.loader;

import dev.necron.tokens.bukkit.NecronTokensPlugin;
import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.builder.MenuBuilder;
import dev.necron.tokens.common.config.node.NodeLoader;
import ninja.leaping.configurate.ConfigurationNode;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MenuLoader {

    private static final String DATA_FOLDER = "plugins/NecronTokens";

    public static Collection<Menu> load() {
        return CompletableFuture.supplyAsync(() -> {
            File parentFile = new File(DATA_FOLDER + "/menu");
            File[] files = parentFile.listFiles();
            if (files == null || files.length == 0) {
                ClassLoader classLoader = NecronTokensPlugin.class.getClassLoader();
                try {
                    NodeLoader.load(DATA_FOLDER + "/menu/shop_selector_menu.yml", classLoader.getResourceAsStream("menu/shop_selector_menu.yml"));
                    NodeLoader.load(DATA_FOLDER + "/menu/example_shop_v1_menu.yml", classLoader.getResourceAsStream("menu/example_shop_v1_menu.yml"));
                    NodeLoader.load(DATA_FOLDER + "/menu/example_shop_v2_menu.yml", classLoader.getResourceAsStream("menu/example_shop_v2_menu.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Collection<Menu> menus = new HashSet<>();
            Arrays.asList(files).forEach(file -> {
                try {
                    ConfigurationNode node = NodeLoader.load(file).getNode("menu");
                    Menu menu = MenuBuilder.of(node, file.getName()).autoBuild();
                    menu.refresh();
                    menus.add(menu);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return menus;
        }).join();
    }
}
