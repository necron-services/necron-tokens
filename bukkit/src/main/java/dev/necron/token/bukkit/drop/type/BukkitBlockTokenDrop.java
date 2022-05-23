package dev.necron.token.bukkit.drop.type;

import dev.necron.token.common.drop.impl.BlockTokenDrop;
import dev.necron.token.common.util.ChanceUtil;
import dev.necron.token.common.util.RandomUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BukkitBlockTokenDrop extends BlockTokenDrop {

    public BukkitBlockTokenDrop(Object dropper, byte dropperData, long minDrop, long maxDrop, double chance) {
        super(dropper, dropperData, minDrop, maxDrop, chance);
    }

    @Override
    public <T, V> void execute(T player, V dropper) {
        if (!(player instanceof Player)) throw new IllegalArgumentException("Player must be a Bukkit Player");
        if (!(dropper instanceof Block)) return;
        Block block = (Block) dropper;
        if (block.getType() != getDropper() || block.getData() != getDropperData()) return;
        if (!(ChanceUtil.tryChance(getChance()))) return;
        long amount = RandomUtil.random(getMinDrop(), getMaxDrop());
        ((Player) player).sendMessage("§6You got §e" + amount + " §6tokens!");
    }

    @Override
    public boolean isSimilar(Object dropper) {
        if (!(dropper instanceof Block)) return false;
        Block block = (Block) dropper;
        return block.getType() == getDropper() && block.getData() == getDropperData();
    }

}
