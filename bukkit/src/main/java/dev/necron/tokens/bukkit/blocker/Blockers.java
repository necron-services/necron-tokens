package dev.necron.tokens.bukkit.blocker;

import dev.necron.tokens.bukkit.blocker.type.WorldBlocker;
import dev.necron.tokens.common.blocker.Blocker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Blockers {

    WorldBlocker(new WorldBlocker());

    private final Blocker instance;

    public static void reload() {
        for (Blockers blocker : values()) {
            blocker.getInstance().init();
        }
    }

}
