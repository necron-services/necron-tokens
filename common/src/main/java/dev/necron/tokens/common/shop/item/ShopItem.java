package dev.necron.tokens.common.shop.item;

import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import dev.necron.tokens.common.shop.item.value.ShopValue;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ShopItem {

    private final Map<String, ShopValue> shopValues = new HashMap<>();
    private final Set<ShopRequirement> shopRequirements = new HashSet<>();

    private int stock;

    private final String name;
    private final int maxStock;

    public ShopItem(String name, int maxStock) {
        this.name = name;
        this.maxStock = maxStock;
        this.stock = maxStock;
    }

    /**
     * player has requirements to buy the item
     * @param playerUUID the player uuid
     * @return true if the player has requirements, false otherwise
     */
    public boolean hasRequirements(UUID playerUUID) {
        for (ShopRequirement requirement : shopRequirements) {
            if (!requirement.has(playerUUID)) return false;
        }
        return true;
    }

    /**
     * execute the requirements if the player meets the requirements
     * @param playerUUID the player uuid
     */
    public void executeRequirements(UUID playerUUID) {
        for (ShopRequirement requirement : shopRequirements) {
            requirement.execute(playerUUID);
        }
    }

    /**
     * execute the values to the player
     * @param playerUUID the player uuid to execute the shop item
     */
    public void execute(UUID playerUUID) {
        for (ShopValue shopValue : shopValues.values()) {
            shopValue.execute(playerUUID);
        }
    }

    /**
     * find the value of the shop item
     * @param name the name of the value
     * @return the value of the shop item
     */
    public Optional<ShopValue> findValue(String name) {
        return Optional.ofNullable(shopValues.get(name));
    }

    /**
     * add a shop value to the shop item
     * @param key the name of the value
     * @param value the value
     */
    public void putValue(String key, ShopValue value) {
        shopValues.put(key, value);
    }

    /**
     * remove a shop value from the shop item
     * @param key the name of the value
     */
    public void removeValue(String key) {
        shopValues.remove(key);
    }

    /**
     * add a shop requirement to the shop item
     * @param requirement the requirement
     */
    public void putRequirement(ShopRequirement requirement) {
        shopRequirements.add(requirement);
    }

    /**
     * remove a shop requirement from the shop item
     * @param requirement the requirement
     */
    public void removeRequirement(ShopRequirement requirement) {
        shopRequirements.remove(requirement);
    }

    /**
     * has stock of the item
     * @return true if the item has stock, false otherwise
     */
    public boolean hasStock() {
        return maxStock <= 0 || stock > 0;
    }

}
