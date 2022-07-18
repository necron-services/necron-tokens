package dev.necron.tokens.bukkit.runnable;

import dev.necron.tokens.bukkit.menu.MenuManager;
import dev.necron.tokens.bukkit.menu.button.MenuButton;

public class MenuRefreshRunnable implements Runnable {

    @Override
    public void run() {
        MenuManager.findAllMenu().forEach(menu -> {
            menu.getMenuButtonMap().forEach((slot, buttons) -> {
                for (MenuButton button : buttons) {
                    if (!button.refresh(menu)) continue;
                    menu.getInventory().setItem(slot, button.getItem());
                }
            });
        });
    }

}
