package dev.necron.tokens.bukkit.drop.type;

import dev.necron.tokens.common.drop.type.EntityTokenDrop;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.util.ChanceUtil;
import dev.necron.tokens.common.util.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitEntityTokenDrop extends EntityTokenDrop {

    public BukkitEntityTokenDrop(Object dropper, double chance, long minDrop, long maxDrop) {
        super(dropper, chance, minDrop, maxDrop);
    }

    @Override
    public <V> void execute(UUID playerUUID, V dropper) {
        if (!(dropper instanceof Entity)) return;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Entity entity = (Entity) dropper;
        if (entity.getType() != getDropper()) return;
        if (!(ChanceUtil.tryChance(getChance()))) return;
        long amount = randomDrop();
        TokenPlayerManager.find(player.getUniqueId()).ifPresent(tokenPlayer -> tokenPlayer.giveTokens(amount));
    }

    @Override
    public <T> void execute(UUID playerUUID, T dropper, long amount) {
        if (!(dropper instanceof Entity)) return;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Entity entity = (Entity) dropper;
        if (entity.getType() != getDropper()) return;
        TokenPlayerManager.find(player.getUniqueId()).ifPresent(tokenPlayer -> tokenPlayer.giveTokens(amount));
    }

    @Override
    public boolean isSimilar(Object dropper) {
        if (!(dropper instanceof Entity)) return false;
        Entity entity = (Entity) dropper;
        return entity.getType() == getDropper();
    }

    @Override
    public long randomDrop() {
        return RandomUtil.random(getMinDrop(), getMaxDrop());
    }

}
