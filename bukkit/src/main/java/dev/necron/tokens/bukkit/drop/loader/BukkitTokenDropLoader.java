package dev.necron.tokens.bukkit.drop.loader;

import com.google.common.base.Enums;
import dev.necron.tokens.bukkit.drop.type.BukkitBlockTokenDrop;
import dev.necron.tokens.bukkit.drop.type.BukkitEntityTokenDrop;
import dev.necron.tokens.common.config.Config;
import dev.necron.tokens.common.config.ConfigManager;
import dev.necron.tokens.common.drop.TokenDrop;
import dev.necron.tokens.common.drop.TokenDropType;
import dev.necron.tokens.common.drop.loader.TokenDropLoader;
import ninja.leaping.configurate.ConfigurationNode;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BukkitTokenDropLoader implements TokenDropLoader {

    @Override
    public Map<TokenDropType, TokenDrop[]> load() {
        return CompletableFuture.supplyAsync(() -> {

            Map<TokenDropType, TokenDrop[]> drops = new HashMap<>();

            Optional<Config> optionalConfig = ConfigManager.createConfig("drops",
                    "drops.yml",
                    getClass().getClassLoader().getResourceAsStream("drops.yml")
            );
            if (!optionalConfig.isPresent()) return drops;
            ConfigurationNode node = optionalConfig.get().getNode().getNode("drops");

            for (TokenDropType tokenDropType : TokenDropType.values()) {
                ConfigurationNode dropNode = node.getNode(tokenDropType.name().toLowerCase(Locale.ROOT));
                if (dropNode.isEmpty() || !dropNode.getNode("enabled").getBoolean()) continue;
                TokenDrop[] tokenDrops = findDrops(tokenDropType, dropNode);
                drops.put(tokenDropType, tokenDrops);
            }

            return drops;

        }).join();
    }

    private static TokenDrop[] findDrops(TokenDropType tokenDropType, ConfigurationNode node) {
        TokenDrop[] tokenDrops = new TokenDrop[node.getChildrenList().size()];
        for (ConfigurationNode child : node.getChildrenMap().values()) {
            String type = String.valueOf(child.getKey());
            if (type.equalsIgnoreCase("enabled")) continue;
            double chance = child.getNode("chance").getDouble();
            long min = child.getNode("min").getLong();
            long max = child.getNode("max").getLong();
            switch (tokenDropType) {
                case BLOCK:
                    if (!Enums.getIfPresent(Material.class, type).isPresent() || !Material.valueOf(type).isBlock()) {
                        System.out.println("[NecronTokens] Invalid block type: " + type);
                        continue;
                    }
                    Material material = Material.valueOf(type);
                    byte data = (byte) child.getNode("data").getInt();
                    BukkitBlockTokenDrop blockTokenDrop = new BukkitBlockTokenDrop(material, data, chance, min, max);
                    tokenDrops = (TokenDrop[]) ArrayUtils.add(tokenDrops, blockTokenDrop);
                    break;
                case ENTITY:
                    if (!Enums.getIfPresent(EntityType.class, type).isPresent()) {
                        System.out.println("[NecronTokens] Invalid entity type: " + type);
                        continue;
                    }
                    EntityType entityType = EntityType.valueOf(type);
                    BukkitEntityTokenDrop entityTokenDrop = new BukkitEntityTokenDrop(entityType, chance, min, max);
                    tokenDrops = (TokenDrop[]) ArrayUtils.add(tokenDrops, entityTokenDrop);
                    break;
            }
        }
        return tokenDrops;
    }

}
