package dev.necron.token.bukkit.drop.loader;

import com.google.common.base.Enums;
import dev.necron.token.bukkit.NecronTokenPlugin;
import dev.necron.token.bukkit.drop.type.BukkitBlockTokenDrop;
import dev.necron.token.bukkit.drop.type.BukkitEntityTokenDrop;
import dev.necron.token.common.config.Config;
import dev.necron.token.common.config.handler.ConfigHandler;
import dev.necron.token.common.drop.TokenDrop;
import dev.necron.token.common.drop.TokenDropType;
import dev.necron.token.common.drop.loader.TokenDropLoader;
import ninja.leaping.configurate.ConfigurationNode;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BukkitTokenDropLoader implements TokenDropLoader {

    private final InputStream stream = NecronTokenPlugin.getInstance().getResource("drops.yml");

    @Override
    public Map<TokenDropType, TokenDrop[]> load() throws Exception {
        return CompletableFuture.supplyAsync(() -> {

            Map<TokenDropType, TokenDrop[]> drops = new HashMap<>();

            Optional<Config> optionalConfig = ConfigHandler.createConfig("drops", "drops.yml", stream);
            if (!optionalConfig.isPresent()) return drops;
            ConfigurationNode node = optionalConfig.get().getNode().getNode("drops");

            ConfigurationNode entityNode = node.getNode("entity");
            TokenDropType entityType = TokenDropType.ENTITY;
            TokenDrop[] entityDrops = new TokenDrop[]{};
            for (ConfigurationNode child : entityNode.getChildrenMap().values()) {
                String entity = String.valueOf(child.getKey());
                if (!Enums.getIfPresent(EntityType.class, entity).isPresent()) {
                    System.out.println("[NecronTokens] Invalid entity type: " + entity);
                } else {
                    EntityType type = EntityType.valueOf(entity);
                    double chance = child.getNode("chance").getDouble();
                    long min = child.getNode("min").getLong();
                    long max = child.getNode("max").getLong();
                    BukkitEntityTokenDrop drop = new BukkitEntityTokenDrop(type, chance, min, max);
                    entityDrops = (TokenDrop[]) ArrayUtils.add(entityDrops, drop);
                }
            }
            drops.put(entityType, entityDrops);

            ConfigurationNode blockNode = node.getNode("block");
            TokenDropType blockType = TokenDropType.BLOCK;
            TokenDrop[] blockDrops = new TokenDrop[blockNode.getChildrenList().size()];
            for (ConfigurationNode child : blockNode.getChildrenMap().values()) {
                String block = String.valueOf(child.getKey());
                if (!Enums.getIfPresent(Material.class, block).isPresent()) {
                    System.out.println("[NecronTokens] Invalid block type: " + block);
                } else {
                    Material type = Material.valueOf(block);
                    if (!type.isBlock()) {
                        System.out.println("[NecronTokens] Invalid block type: " + block);
                    } else {
                        double chance = child.getNode("chance").getDouble();
                        byte data = (byte) child.getNode("data").getInt();
                        long min = child.getNode("min").getLong();
                        long max = child.getNode("max").getLong();
                        BukkitBlockTokenDrop drop = new BukkitBlockTokenDrop(type, data, chance, min, max);
                        blockDrops = (TokenDrop[]) ArrayUtils.add(blockDrops, drop);
                    }
                }
            }
            drops.put(blockType, blockDrops);

            return drops;

        }).get();
    }

}
