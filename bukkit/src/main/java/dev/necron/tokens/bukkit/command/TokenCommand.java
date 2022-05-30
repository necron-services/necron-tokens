package dev.necron.tokens.bukkit.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import dev.necron.tokens.bukkit.NecronTokensPlugin;
import dev.necron.tokens.bukkit.menu.handler.MenuHandler;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.key.ConfigKeys;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("necrontokens|tokens|token")
public class TokenCommand extends BaseCommand {

    private final NecronTokensPlugin plugin = NecronTokensPlugin.getInstance();

    @HelpCommand
    @Description("Shows this help message")
    public void onHelp(CommandSender sender) {
        if (!sender.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) {
            sender.sendMessage("");
            sender.sendMessage("");
            sender.sendMessage("§6Necron Tokens");
            sender.sendMessage("§6Version§8: §e" + plugin.getDescription().getVersion());
            sender.sendMessage("§6Author§8: §eKafein");
            sender.sendMessage("§6Site§8: §ehttps://necron.dev/");
            sender.sendMessage("");
            return;
        }
        LanguageUtil.replaceList(ConfigKeys.Language.HELP_MESSAGE.getValue()).forEach(sender::sendMessage);
    }

    @Subcommand("reload")
    @Description("Reloads Necron Tokens")
    public void onReload(CommandSender sender) {
        if (!sender.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PERMISSION.getValue()));
            return;
        }
        plugin.reloadConfigs();
        sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.RELOADED.getValue()));
    }

    @Subcommand("balance")
    @Description("Views your and other players balance")
    public void get(CommandSender sender, String[] args) {
        if (!sender.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PERMISSION.getValue()));
            return;
        }
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PLAYER.getValue()));
                return;
            }
            Player player = (Player) sender;
            //TODO view player balance
            return;
        }
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.PLAYER_NOT_FOUND.getValue()));
            return;
        }
        //TODO: Add get tokens command
    }

    @Subcommand("shop")
    @Description("Open shop")
    public void onShop(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PLAYER.getValue()));
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) {
            player.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PERMISSION.getValue()));
            return;
        }
        MenuHandler.findShopSelectorMenu().ifPresent(menu -> menu.open(player));
    }

}
