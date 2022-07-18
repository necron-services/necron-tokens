package dev.necron.tokens.bukkit.command.admin;

import com.hakan.core.command.HCommandAdapter;
import com.hakan.core.command.executors.base.BaseCommand;
import com.hakan.core.command.executors.sub.SubCommand;
import dev.necron.tokens.bukkit.blocker.Blockers;
import dev.necron.tokens.bukkit.drop.loader.BukkitTokenDropLoader;
import dev.necron.tokens.bukkit.event.admin.TokenSetEvent;
import dev.necron.tokens.bukkit.menu.MenuManager;
import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.category.MessageCategories;
import dev.necron.tokens.bukkit.message.category.type.ShopMessages;
import dev.necron.tokens.bukkit.shop.loader.BukkitShopLoader;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.ConfigManager;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.drop.TokenDropManager;
import dev.necron.tokens.common.player.TokenPlayer;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.shop.Shop;
import dev.necron.tokens.common.shop.ShopManager;
import dev.necron.tokens.common.util.formatter.TokenFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Optional;
import java.util.stream.Collectors;

@BaseCommand(
        name = "necrontokens",
        description = "Tokens admin command",
        aliases = {"necrontokens", "tokensplugin", "tokenplugin", "tokensadmin", "tokenadmin"}
)
public class TokenAdminCommand implements HCommandAdapter {

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    @SubCommand
    public void helpCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return;
        LanguageUtil.replaceList(ConfigKeys.Language.HELP_ADMIN_MESSAGE.getValue()).forEach(sender::sendMessage);
    }

    @SubCommand(
            args = "reload"
    )
    public void reloadCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return;

        ConfigManager.init();
        ShopManager.init(new BukkitShopLoader());
        TokenDropManager.init(new BukkitTokenDropLoader());
        MenuManager.init();

        Blockers.reload();
        MessageCategories.reload();

        sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.RELOADED.getValue()));
    }

    @SubCommand(
            args = "refresh-shops"
    )
    public void refreshShopsCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return;

        ShopManager.findAll().forEach(Shop::refresh);

        if (sender instanceof Player) {
            ShopMessages shopMessages = (ShopMessages) MessageCategories.SHOP_MESSAGES.getInstance();
            Message message = shopMessages.SHOPS_HAS_BEEN_REFRESHED;
            message.execute((Player) sender,
                    () -> LanguageUtil.replace(message.getValue())
            );
        }
    }

    @SubCommand(
            args = "refresh-shop"
    )
    public void refreshShopCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return;
        if (args.length < 2) {
            sender.sendMessage("/necrontokens refresh-shop <shop-name>");
            return;
        }

        String shopName = args[1];
        Optional<Shop> optionalShop = ShopManager.find(shopName);
        if (!optionalShop.isPresent()) {
            sender.sendMessage("§cShop not found");
            sender.sendMessage("§7Available shops: §a" +
                    ShopManager.findAll().stream()
                            .map(Shop::getName)
                            .collect(Collectors.toSet())
            );
            return;
        }

        Shop shop = optionalShop.get();
        shop.refresh();
        if (sender instanceof Player) {
            ShopMessages shopMessages = (ShopMessages) MessageCategories.SHOP_MESSAGES.getInstance();
            Message message = shopMessages.SHOP_HAS_BEEN_REFRESHED;
            message.execute((Player) sender,
                    () -> LanguageUtil.replace(message.getValue(),
                            new String[]{"%shop%"},
                            new String[]{args[1]}
                    )
            );
        }
    }

    @SubCommand(
            args = "give"
    )
    public void giveTokensCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return;

        if (args.length < 3) {
            sender.sendMessage("/necrontokens give <player> <amount>");
            return;
        }
        String playerName = args[1];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.PLAYER_NOT_FOUND.getValue()));
            return;
        }
        try {
            long amount = Long.parseLong(args[2]);
            TokenPlayer tokenPlayer = TokenPlayerManager.get(player.getUniqueId());

            TokenSetEvent event = new TokenSetEvent(sender,
                    player,
                    tokenPlayer.getTokens(),
                    tokenPlayer.getTokens() + amount,
                    false);
            pluginManager.callEvent(event);
            if (!event.isCancelled()) {
                tokenPlayer.giveTokens(amount);

                sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.ADMIN_TOKEN_ADDED_MESSAGE.getValue(),
                        new String[]{"%player%", "%amount%"},
                        new String[]{playerName, TokenFormatter.format(amount)}
                ));
            }
        } catch (Exception e) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.AMOUNT_NOT_VALID_MESSAGE.getValue()));
        }
    }

    @SubCommand(
            args = "take"
    )
    public void takeTokensCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return;

        if (args.length < 3) {
            sender.sendMessage("/necrontokens take <player> <amount>");
            return;
        }
        String playerName = args[1];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.PLAYER_NOT_FOUND.getValue()));
            return;
        }
        try {
            long amount = Long.parseLong(args[2]);
            TokenPlayer tokenPlayer = TokenPlayerManager.get(player.getUniqueId());

            TokenSetEvent event = new TokenSetEvent(sender,
                    player,
                    tokenPlayer.getTokens(),
                    tokenPlayer.getTokens() - amount,
                    false);
            pluginManager.callEvent(event);
            if (!event.isCancelled()) {
                tokenPlayer.takeTokens(amount);

                sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.ADMIN_TOKEN_REMOVED_MESSAGE.getValue(),
                        new String[]{"%player%", "%amount%"},
                        new String[]{playerName, TokenFormatter.format(amount)}
                ));
            }
        } catch (Exception e) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.AMOUNT_NOT_VALID_MESSAGE.getValue()));
        }
    }

    @SubCommand(
            args = "set"
    )
    public void setTokensCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) return;

        if (args.length < 3) {
            sender.sendMessage("/necrontokens set <player> <amount>");
            return;
        }
        String playerName = args[1];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.PLAYER_NOT_FOUND.getValue()));
            return;
        }
        try {
            long amount = Long.parseLong(args[2]);
            TokenPlayer tokenPlayer = TokenPlayerManager.get(player.getUniqueId());

            TokenSetEvent event = new TokenSetEvent(sender,
                    player,
                    tokenPlayer.getTokens(),
                    amount,
                    false);
            pluginManager.callEvent(event);
            if (!event.isCancelled()) {
                tokenPlayer.setTokens(amount);

                sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.ADMIN_TOKEN_SET_MESSAGE.getValue(),
                        new String[]{"%player%", "%amount%"},
                        new String[]{playerName, TokenFormatter.format(amount)}
                ));
            }
        } catch (Exception e) {
            sender.sendMessage(LanguageUtil.replace(ConfigKeys.Language.AMOUNT_NOT_VALID_MESSAGE.getValue()));
        }
    }

    private boolean hasPermission(CommandSender sender) {
        if (sender.hasPermission(ConfigKeys.Settings.ADMIN_PERM.getValue())) return true;

        Plugin plugin = Bukkit.getPluginManager().getPlugin("NecronTokens");
        sender.sendMessage("");
        sender.sendMessage("");
        sender.sendMessage("§6Necron Tokens");
        sender.sendMessage("§6Version§8: §e" + plugin.getDescription().getVersion());
        sender.sendMessage("§6Author§8: §eKafein");
        sender.sendMessage("§6Site§8: §enecron.dev");
        sender.sendMessage("");
        return false;
    }

}
