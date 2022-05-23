package dev.necron.token.bukkit.drop.type;

import dev.necron.token.common.drop.type.EntityTokenDrop;
import dev.necron.token.common.util.ChanceUtil;
import dev.necron.token.common.util.RandomUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class BukkitEntityTokenDrop extends EntityTokenDrop {

    public BukkitEntityTokenDrop(Object dropper, double chance, long minDrop, long maxDrop) {
        super(dropper, chance, minDrop, maxDrop);
    }

    @Override
    public <T, V> void execute(T player, V dropper) {
        if (!(player instanceof Player)) throw new IllegalArgumentException("Player must be a Bukkit Player");
        if (!(dropper instanceof Entity)) return;
        Entity entity = (Entity) dropper;
        if (entity.getType() != getDropper()) return;
        if (!(ChanceUtil.tryChance(getChance()))) return;
        long amount = RandomUtil.random(getMinDrop(), getMaxDrop());
        ((Player) player).sendMessage("§6You got §e" + amount + " §6tokens!");
    }

    @Override
    public boolean isSimilar(Object dropper) {
        if (!(dropper instanceof Entity)) return false;
        Entity entity = (Entity) dropper;
        return entity.getType() == getDropper();
    }

}
