package dev.necron.tokens.bukkit.event.shop;

import dev.necron.tokens.common.drop.TokenDropType;
import dev.necron.tokens.common.shop.Shop;
import dev.necron.tokens.common.shop.item.ShopItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ShopTransactionEvent extends Event {

    @Getter private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final Shop shop;
    private final ShopItem shopItem;
    private boolean cancelled;

    public ShopTransactionEvent(Player player, Shop shop, ShopItem shopItem, boolean cancelled) {
        this.player = player;
        this.shop = shop;
        this.shopItem = shopItem;
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
