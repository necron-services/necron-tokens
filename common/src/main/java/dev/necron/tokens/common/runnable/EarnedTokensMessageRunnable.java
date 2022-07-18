package dev.necron.tokens.common.runnable;


import dev.necron.tokens.common.config.key.ConfigKeys;

import java.util.function.BiConsumer;

public class EarnedTokensMessageRunnable implements Runnable{

    private BiConsumer<Integer, Boolean> consumer;

    @Override
    public void run() {
        boolean enabled = ConfigKeys.Settings.EARNED_TOKENS_MESSAGE_ENABLED.getValue();
        if (!enabled) return;

        int timerValue = ConfigKeys.Settings.EARNED_TOKENS_MESSAGE_TIMER.getValue();
        boolean resetEarnedTokens = ConfigKeys.Settings.RESET_PLAYERS_EARNED_TOKENS.getValue();
        consumer.accept(timerValue, resetEarnedTokens);
    }

    public void onExecute(BiConsumer<Integer, Boolean> consumer) {
        this.consumer = consumer;
    }

}
