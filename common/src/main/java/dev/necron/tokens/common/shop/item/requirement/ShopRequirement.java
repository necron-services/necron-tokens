package dev.necron.tokens.common.shop.item.requirement;

import java.util.UUID;

public interface ShopRequirement {

    /**
     * Checks if the player meets the requirements to buy the item
     * @param playerUUID The UUID of the player
     * @return True if the player meets the requirements, false otherwise
     */
    boolean has(UUID playerUUID);

    /**
     * Execute the requirements if the player meets the requirements
     * @param playerUUID The UUID of the player
     */
    void execute(UUID playerUUID);

    /**
     * Get the name of the requirement for configurations
     * @return The name of the requirement
     */
    String getName();

}
