package dev.necron.tokens.bukkit.listener;

import dev.necron.tokens.bukkit.menu.handler.MenuHandler;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        String title = event.getView().getTitle();
        MenuHandler.findByTitle(title, true).ifPresent(menu -> {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null) return;
            Player player = (Player) event.getWhoClicked();
            menu.findButton(event.getSlot()).ifPresent(button -> {
                if (button.getClickSound() != null) player.playSound(player.getLocation(), button.getClickSound(), 1, 1);
                button.getClickEvent().accept(event);
            });
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        String title = event.getView().getTitle();
        MenuHandler.findByTitle(title, true).ifPresent(menu -> {
            Player player = (Player) event.getPlayer();
            Sound sound = menu.getOpenSound();
            if (sound != null) player.playSound(player.getLocation(), sound, 1, 1);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        String title = event.getView().getTitle();
        MenuHandler.findByTitle(title, true).ifPresent(menu -> {
            Player player = (Player) event.getPlayer();
            Sound sound = menu.getCloseSound();
            if (sound != null) player.playSound(player.getLocation(), sound, 1, 1);
        });
    }

}
