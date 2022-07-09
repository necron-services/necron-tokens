package dev.necron.tokens.bukkit.event.admin;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class TokenSetEvent extends Event {

    @Getter private static final HandlerList handlerList = new HandlerList();

    private final CommandSender admin;
    private final Player target;
    private long oldTokens, newTokens;
    private boolean cancelled;

    public TokenSetEvent(CommandSender admin, Player target, long oldTokens, long newTokens, boolean cancelled) {
        this.admin = admin;
        this.target = target;
        this.oldTokens = oldTokens;
        this.newTokens = newTokens;
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
