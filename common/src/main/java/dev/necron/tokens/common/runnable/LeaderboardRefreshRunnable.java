package dev.necron.tokens.common.runnable;

import dev.necron.tokens.common.leaderboard.Leaderboard;

public class LeaderboardRefreshRunnable implements Runnable{

    @Override
    public void run() {
        long timeLeft = Leaderboard.getRegenTime();
        if (timeLeft <= 0) {
            Leaderboard.reload();
        }
    }

}
