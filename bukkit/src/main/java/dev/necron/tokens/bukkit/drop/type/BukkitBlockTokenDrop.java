package dev.necron.tokens.bukkit.drop.type;

import dev.necron.tokens.common.drop.type.BlockTokenDrop;
import dev.necron.tokens.common.player.TokenPlayerManager;
import dev.necron.tokens.common.util.ChanceUtil;
import dev.necron.tokens.common.util.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitBlockTokenDrop extends BlockTokenDrop {

    public BukkitBlockTokenDrop(Object dropper, byte dropperData, double chance, long minDrop, long maxDrop) {
        super(dropper, dropperData, chance, minDrop, maxDrop);
    }

    @Override
    public <V> void execute(UUID playerUUID, V dropper) {
        if (!(dropper instanceof Block)) return;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Block block = (Block) dropper;
        if (block.getType() != getDropper() || block.getData() != getDropperData()) return;
        if (!(ChanceUtil.tryChance(getChance()))) return;
        long amount = randomDrop();
        TokenPlayerManager.find(player.getUniqueId()).ifPresent(tokenPlayer -> tokenPlayer.giveTokens(amount));
    }

    @Override
    public <T> void execute(UUID playerUUID, T dropper, long amount) {
        if (!(dropper instanceof Block)) return;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;
        Block block = (Block) dropper;
        if (block.getType() != getDropper() || block.getData() != getDropperData()) return;
        TokenPlayerManager.find(player.getUniqueId()).ifPresent(tokenPlayer -> tokenPlayer.giveTokens(amount));
    }

    @Override
    public boolean isSimilar(Object dropper) {
        if (!(dropper instanceof Block)) return false;
        Block block = (Block) dropper;
        return block.getType() == getDropper() && block.getData() == getDropperData();
    }

    @Override
    public long randomDrop() {
        return RandomUtil.random(getMinDrop(), getMaxDrop());
    }

}
