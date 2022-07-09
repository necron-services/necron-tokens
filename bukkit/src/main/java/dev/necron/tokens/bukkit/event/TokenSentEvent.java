package dev.necron.tokens.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class TokenSentEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Player player, target;
    private long amount;
    private boolean cancelled;

    public TokenSentEvent(Player player, Player target, long amount, boolean cancelled) {
        this.player = player;
        this.target = target;
        this.amount = amount;
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
