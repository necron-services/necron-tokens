package dev.necron.tokens.bukkit.drop.type;

import dev.necron.tokens.common.drop.type.EntityTokenDrop;
import dev.necron.tokens.common.token.TokenPlayerHandler;
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
        long amount = RandomUtil.random(getMinDrop(), getMaxDrop());
        player.sendMessage("§6You got §e" + amount + " §6tokens!");
        TokenPlayerHandler.find(player.getUniqueId()).ifPresent(tokenPlayer -> tokenPlayer.giveTokens(amount));
    }

    @Override
    public boolean isSimilar(Object dropper) {
        if (!(dropper instanceof Entity)) return false;
        Entity entity = (Entity) dropper;
        return entity.getType() == getDropper();
    }

}
