package dev.necron.tokens.bukkit.blocker.type;

import dev.necron.tokens.common.blocker.Blocker;
import dev.necron.tokens.common.config.key.ConfigKeys;

import java.util.HashSet;
import java.util.List;

public class WorldBlocker extends Blocker {
    @Override
    public void init() {
        enabled = ConfigKeys.Settings.WORLD_BLOCKER_ENABLED.getValue();
        type = Type.of(ConfigKeys.Settings.WORLD_BLOCKER_TYPE.getValue());

        List<String> list = ConfigKeys.Settings.BLOCKED_WORLDS.getValue();
        blocked = new HashSet<>(list);
    }
}
