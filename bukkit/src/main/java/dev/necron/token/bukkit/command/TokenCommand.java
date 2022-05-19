package dev.necron.token.bukkit.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import dev.necron.token.bukkit.NecronTokenPlugin;
import dev.necron.token.bukkit.util.LanguageUtil;
import dev.necron.token.api.config.ConfigKeys;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("necrontoken|token")
public class TokenCommand extends BaseCommand {

    private final NecronTokenPlugin plugin = NecronTokenPlugin.getInstance();

    @HelpCommand
    @Description("Shows this help message")
    public void help(CommandSender sender) {
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
    public void reload(CommandSender sender) {
        if (!sender.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PERMISSION.getValue()));
            return;
        }
        plugin.reloadConfigs();
        sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.RELOADED.getValue()));
    }

    @Subcommand("get")
    @Description("Get target's tokens")
    public void get(CommandSender sender, String[] args) {
        if (!sender.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PERMISSION.getValue()));
            return;
        }
        if (args.length == 0) {
            sender.sendMessage("§6/token get <player>");
            return;
        }
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.PLAYER_NOT_FOUND.getValue()));
            return;
        }
        //TODO: Add get tokens command
    }

}
