package dev.necron.tokens.bukkit.command;

import com.hakan.core.command.HCommandAdapter;
import com.hakan.core.command.executors.base.BaseCommand;
import com.hakan.core.command.executors.sub.SubCommand;
import dev.necron.tokens.bukkit.event.TokenReceiveEvent;
import dev.necron.tokens.bukkit.event.TokenSentEvent;
import dev.necron.tokens.bukkit.menu.MenuManager;
import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.category.MessageCategories;
import dev.necron.tokens.bukkit.message.category.type.TokenMessages;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.player.TokenPlayer;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.util.formatter.TokenFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

@BaseCommand(
        name = "tokens",
        description = "Tokens command",
        aliases = {"tokens", "token"}
)
public class TokenCommand implements HCommandAdapter {

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    @SubCommand()
    public void helpCommand(CommandSender sender, String[] args) {
        LanguageUtil.replaceList(ConfigKeys.Language.HELP_MESSAGE.getValue()).forEach(sender::sendMessage);
    }

    @SubCommand(
            args = "view"
    )
    public void viewCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PLAYER.getValue()));
            return;
        }
        Player player = (Player) sender;
        Player target;
        if (args.length == 1) target = (Player) sender;
        else {
            target = Bukkit.getServer().getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.PLAYER_NOT_FOUND.getValue()));
                return;
            }
        }
        TokenPlayer tokenPlayer = TokenPlayerManager.get(target.getUniqueId());

        TokenMessages tokenMessages = (TokenMessages) MessageCategories.TOKEN_MESSAGES.getInstance();
        Message message = tokenMessages.TOKEN_VIEW;
        message.execute(player, () -> LanguageUtil.replace(message.getValue(),
                new String[]{"%player%", "%amount%"},
                new String[]{target.getName(), tokenPlayer.getTokensFormatted()}
        ));
    }

    @SubCommand(
            args = "pay"
    )
    public void payCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PLAYER.getValue()));
            return;
        }
        if (args.length < 2) {
            sender.sendMessage("/tokens pay <player> <amount>");
            return;
        }
        TokenMessages tokenMessages = (TokenMessages) MessageCategories.TOKEN_MESSAGES.getInstance();

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(LanguageUtil.replace(ConfigKeys.Language.PLAYER_NOT_FOUND.getValue()));
            return;
        }
        if (player.getUniqueId().equals(target.getUniqueId())) {
            Message message = tokenMessages.CANNOT_SEND_TO_SELF;
            message.execute(player, () -> LanguageUtil.replace(message.getValue()));
            return;
        }
        try {
            long amount = Long.parseLong(args[2]);
            TokenPlayer tokenPlayer = TokenPlayerManager.get(player.getUniqueId());
            if (tokenPlayer.getTokens() < amount) {
                Message message = tokenMessages.NOT_ENOUGH_TOKENS;
                message.execute(player, () -> LanguageUtil.replace(message.getValue()));
            } else {
                TokenSentEvent sentEvent = new TokenSentEvent(player, target, amount, false);
                pluginManager.callEvent(sentEvent);
                if (!sentEvent.isCancelled()) {
                    tokenPlayer.takeTokens(sentEvent.getAmount());

                    Message message = tokenMessages.TOKEN_SENT;
                    message.execute(player, () -> LanguageUtil.replace(message.getValue(),
                            new String[]{"%player%", "%amount%"},
                            new String[]{target.getName(), TokenFormatter.format(sentEvent.getAmount())}
                    ));
                }

                TokenReceiveEvent receiveEvent = new TokenReceiveEvent(target, player, amount, false);
                pluginManager.callEvent(receiveEvent);
                if (!receiveEvent.isCancelled()) {
                    TokenPlayerManager.get(target.getUniqueId()).giveTokens(receiveEvent.getAmount());

                    Message message = tokenMessages.TOKEN_RECEIVED;
                    message.execute(player, () -> LanguageUtil.replace(message.getValue(),
                            new String[]{"%player%", "%amount%"},
                            new String[]{player.getName(), TokenFormatter.format(receiveEvent.getAmount())}
                    ));
                }
            }
        } catch (Exception e) {
            Message message = tokenMessages.AMOUNT_NOT_VALID;
            message.execute(player, () -> LanguageUtil.replace(message.getValue()));
        }
    }

    @SubCommand(
            args = "shop"
    )
    public void shopCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.NO_PLAYER.getValue()));
            return;
        }
        Player player = (Player) sender;
        MenuManager.findMenuShopSelectorMenu().ifPresent(menu -> menu.open(player));
    }

}
