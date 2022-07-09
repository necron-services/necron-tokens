package dev.necron.tokens.bukkit.menu;

import dev.necron.tokens.bukkit.menu.loader.MenuLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class MenuManager {

    private static final Map<String, Menu> menus = new HashMap<>();
    private static final Map<UUID, String> menuViewers = new HashMap<>();

    public static void init() {
        menus.clear();
        if (!menuViewers.isEmpty()) {
            menuViewers.keySet().forEach(uuid -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) player.closeInventory();
            });
        }
        try {
            MenuLoader.load().forEach(menu -> menus.put(menu.getName(), menu));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Collection<Menu> findAllMenu() {
        return menus.values();
    }

    public static Collection<UUID> findAllViewers() {
        return menuViewers.keySet();
    }

    public static Optional<Menu> findMenuShopSelectorMenu() {
        return menus.values().stream()
                .filter(Menu::isShopSelector)
                .findFirst();
    }

    public static Optional<Menu> findMenuByName(String name) {
        return menus.values().stream()
                .filter(menu -> menu.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public static Optional<Menu> findMenuByTitle(String title, boolean colored) {
        return menus.values().stream()
                .filter(menu -> colored
                        ? menu.getColoredTitle().equalsIgnoreCase(title)
                        : menu.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public static Optional<Menu> findMenuByShopName(String shopName) {
        return menus.values().stream()
                .filter(menu -> menu.getShopName() != null && menu.getShopName().equalsIgnoreCase(shopName))
                .findFirst();
    }

    public static Optional<Menu> findMenuByViewer(UUID viewerUUID) {
        if (!menuViewers.containsKey(viewerUUID)) return Optional.empty();
        return findMenuByName(menuViewers.get(viewerUUID));
    }

    public static void putMenu(String name, Menu menu) {
        menus.put(name.toUpperCase(Locale.ROOT), menu);
    }

    public static void removeMenu(String name) {
        menus.remove(name.toUpperCase(Locale.ROOT));
    }

    public static void putViewer(UUID viewerUUID, String menuName) {
        menuViewers.put(viewerUUID, menuName.toUpperCase(Locale.ROOT));
    }

    public static void removeViewer(UUID viewerUUID) {
        menuViewers.remove(viewerUUID);
    }

}
