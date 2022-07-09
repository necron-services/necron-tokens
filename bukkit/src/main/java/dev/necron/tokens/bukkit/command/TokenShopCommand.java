package dev.necron.tokens.bukkit.command;

import com.hakan.core.command.HCommandAdapter;
import com.hakan.core.command.executors.base.BaseCommand;
import com.hakan.core.command.executors.sub.SubCommand;
import dev.necron.tokens.bukkit.menu.MenuManager;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.key.ConfigKeys;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@BaseCommand(
        name = "tokenshop",
        description = "Token shop command",
        aliases = {"tokensmarket", "tokenmarket"}
)
public class TokenShopCommand implements HCommandAdapter {

    @SubCommand()
    public void command(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PLAYER.getValue()));
            return;
        }
        Player player = (Player) sender;
        MenuManager.findMenuShopSelectorMenu().ifPresent(menu -> menu.open(player));
    }

}
