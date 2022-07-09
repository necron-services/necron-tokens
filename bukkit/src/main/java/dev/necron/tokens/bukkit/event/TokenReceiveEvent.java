package dev.necron.tokens.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class TokenReceiveEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Player player, sender;
    private long amount;
    private boolean cancelled;

    public TokenReceiveEvent(Player player, Player sender, long amount, boolean cancelled) {
        this.player = player;
        this.sender = sender;
        this.amount = amount;
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
