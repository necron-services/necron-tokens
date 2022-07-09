package dev.necron.tokens.bukkit.runnable;

import dev.necron.tokens.bukkit.message.Message;
import dev.necron.tokens.bukkit.message.category.MessageCategories;
import dev.necron.tokens.bukkit.message.category.type.RewardMessages;
import dev.necron.tokens.bukkit.util.language.LanguageUtil;
import dev.necron.tokens.common.config.key.ConfigKey;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.util.formatter.TokenFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EarnedTokensMessageRunnable implements Runnable{

    @Override
    public void run() {

        boolean enabled = ConfigKeys.Settings.EARNED_TOKENS_MESSAGE_ENABLED.getValue();
        if (!enabled) return;

        int timerValue = ConfigKeys.Settings.EARNED_TOKENS_MESSAGE_TIMER.getValue();
        boolean resetEarnedTokens = ConfigKeys.Settings.RESET_PLAYERS_EARNED_TOKENS.getValue();

        RewardMessages messages = (RewardMessages) MessageCategories.REWARD_MESSAGES.getInstance();
        Message message = messages.EARNED_TOKENS_LAST_TIME;

        TokenPlayerManager.findAll().forEach(tokenPlayer -> {
            long joinedTimeMillis = tokenPlayer.getJoinedTimeMillis();
            long seconds = (System.currentTimeMillis() - joinedTimeMillis) / 1000;
            if (seconds != 0 && seconds % timerValue == 0) {
                Player player = Bukkit.getPlayer(tokenPlayer.getUuid());
                if (player == null) return;

                message.execute(player, () -> LanguageUtil.replace(message.getValue(),
                        new String[]{"%amount%"},
                        new String[]{TokenFormatter.format(tokenPlayer.getEarnedTokens())})
                );

                if (resetEarnedTokens) tokenPlayer.setEarnedTokens(0);
            }
        });

    }

}
