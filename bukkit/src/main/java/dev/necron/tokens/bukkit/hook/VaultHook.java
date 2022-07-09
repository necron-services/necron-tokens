package dev.necron.tokens.bukkit.hook;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class VaultHook {

    @Getter private static Economy economy;
    @Getter private static Permission permission;

    static {
        ServicesManager servicesManager = Bukkit.getServicesManager();
        setupEconomy(servicesManager);
        setupPermissions(servicesManager);
    }

    private static void setupEconomy(ServicesManager servicesManager) {
        RegisteredServiceProvider<Economy> rsp = servicesManager.getRegistration(Economy.class);
        if (rsp != null) economy = rsp.getProvider();
    }

    private static void setupPermissions(ServicesManager servicesManager) {
        RegisteredServiceProvider<Permission> rsp = servicesManager.getRegistration(Permission.class);
        if (rsp != null) permission = rsp.getProvider();
    }

}
