package dev.necron.tokens.bukkit.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class TokenGiveEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final CommandSender sender;
    private long amount;
    private boolean cancelled;

    public TokenGiveEvent(Player player, CommandSender sender, long amount, boolean cancelled) {
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
