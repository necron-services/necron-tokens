package dev.necron.token.bukkit.drop.type;

import dev.necron.token.common.drop.type.BlockTokenDrop;
import dev.necron.token.common.token.TokenPlayerHandler;
import dev.necron.token.common.util.ChanceUtil;
import dev.necron.token.common.util.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitBlockTokenDrop extends BlockTokenDrop {

    public BukkitBlockTokenDrop(Object dropper, byte dropperData, double chance, long minDrop, long maxDrop) {
        super(dropper, dropperData, chance, minDrop, maxDrop);
    }

    @Override
    public <T, V> void execute(UUID playerUUID, V dropper) {
        if (!(dropper instanceof Block)) return;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Block block = (Block) dropper;
        if (block.getType() != getDropper() || block.getData() != getDropperData()) return;
        if (!(ChanceUtil.tryChance(getChance()))) return;
        long amount = RandomUtil.random(getMinDrop(), getMaxDrop());
        player.sendMessage("§6You got §e" + amount + " §6tokens!");
        TokenPlayerHandler.find(player.getUniqueId()).ifPresent(tokenPlayer -> tokenPlayer.addTokens(amount));
    }

    @Override
    public boolean isSimilar(Object dropper) {
        if (!(dropper instanceof Block)) return false;
        Block block = (Block) dropper;
        return block.getType() == getDropper() && block.getData() == getDropperData();
    }

}
