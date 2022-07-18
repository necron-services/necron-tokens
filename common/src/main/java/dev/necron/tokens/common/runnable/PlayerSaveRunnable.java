package dev.necron.tokens.common.runnable;

import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.player.TokenPlayer;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.storage.Storage;
import dev.necron.tokens.common.storage.StorageProvider;

import java.util.Collection;
import java.util.function.Consumer;

public class PlayerSaveRunnable implements Runnable {

    private Consumer<Integer> consumer;
    private long duration;

    public PlayerSaveRunnable() {
        this.duration = createDuration();
    }

    @Override
    public void run() {
        boolean enabled = ConfigKeys.Settings.AUTO_SAVE_USERS_ENABLED.getValue();
        if (!enabled) return;

        long interval = (duration - System.currentTimeMillis()) / 1000;
        if (interval <= 0) {
            duration = createDuration();
            execute();
        }
    }

    private void execute() {
        Collection<TokenPlayer> tokenPlayers = TokenPlayerManager.findAll();
        consumer.accept(tokenPlayers.size());

        Storage storage = StorageProvider.getStorage();
        storage.savePlayers(tokenPlayers);
    }

    public void onExecute(Consumer<Integer> consumer) {
        this.consumer = consumer;
    }

    private long createDuration() {
        return System.currentTimeMillis() + (ConfigKeys.Settings.AUTO_SAVE_USERS_INTERVAL.getValue() * 1000);
    }

}
