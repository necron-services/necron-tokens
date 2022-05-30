package dev.necron.tokens.bukkit.menu.handler;

import dev.necron.tokens.bukkit.menu.Menu;
import dev.necron.tokens.bukkit.menu.loader.MenuLoader;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class MenuHandler {

    private static final Map<String, Menu> menus = new HashMap<>();

    public static void init() {
        menus.clear();
        try {
            MenuLoader.load().forEach(menu -> menus.put(menu.getName(), menu));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Optional<Menu> findShopSelectorMenu() {
        return menus.values().stream().filter(Menu::isShopSelector).findFirst();
    }

    public static Optional<Menu> findByName(String name) {
        return Optional.ofNullable(menus.get(name.toUpperCase(Locale.ROOT)));
    }

    public static Optional<Menu> findByTitle(String title, boolean colored) {
        return menus.values().stream()
                .filter(menu -> colored
                        ? menu.getColoredTitle().equalsIgnoreCase(title)
                        : menu.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public static Optional<Menu> findByShopName(String shopName) {
        return menus.values().stream()
                .filter(menu -> menu.getShopName() != null && menu.getShopName().equalsIgnoreCase(shopName))
                .findFirst();
    }

    public static void put(String name, Menu menu) {
        menus.put(name.toUpperCase(Locale.ROOT), menu);
    }

    public static void remove(String name) {
        menus.remove(name.toUpperCase(Locale.ROOT));
    }

}
