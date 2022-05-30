package dev.necron.tokens.bukkit.hook;

import dev.necron.tokens.bukkit.NecronTokensPlugin;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    @Getter private static Economy economy;
    @Getter private static Permission permission;

    static {
        Plugin plugin = NecronTokensPlugin.getInstance();
        setupEconomy(plugin);
        setupPermissions(plugin);
    }

    private static void setupEconomy(final Plugin plugin) {
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) economy = rsp.getProvider();
    }

    private static void setupPermissions(final Plugin plugin) {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp != null) permission = rsp.getProvider();
    }

}
