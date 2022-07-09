package dev.necron.tokens.common.blocker;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Set;

public abstract class Blocker {

    protected Set<String> blocked;
    protected Type type;
    @Getter protected boolean enabled;

    public abstract void init();

    public boolean control(String name) {
        if (!enabled) return false;
        switch (type) {
            case BLACKLIST: return blocked.contains(name);
            case WHITELIST: return !blocked.contains(name);
            default: return false;
        }
    }

    public enum Type {
        WHITELIST, BLACKLIST;

        @Nullable
        public static Type of(String name) {
            switch (name.toLowerCase(Locale.ROOT)) {
                case "whitelist": return WHITELIST;
                case "blacklist": return BLACKLIST;
                default: return null;
            }
        }
    }

}
