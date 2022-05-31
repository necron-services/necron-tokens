package dev.necron.tokens.bukkit.event;

import dev.necron.tokens.common.drop.TokenDropType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class TokenDropEvent extends Event {

    @Getter private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final TokenDropType cause;
    private long amount;
    private boolean cancelled;

    public TokenDropEvent(Player player, TokenDropType cause, long amount, boolean cancelled) {
        this.player = player;
        this.cause = cause;
        this.amount = amount;
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
