package dev.necron.tokens.bukkit.shop.item.requirement;

import dev.necron.tokens.bukkit.shop.item.requirement.type.*;
import dev.necron.tokens.common.shop.item.requirement.ShopRequirement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
@Getter
public enum BukkitShopRequirementType {
    TOKEN(TokenShopRequirement.class),
    GROUP(GroupShopRequirement.class),
    PERM(PermissionShopRequirement.class),
    MONEY(MoneyShopRequirement.class),
    EXP(ExperienceShopRequirement.class);

    private final Class<?> clazz;

    @Nullable
    public static BukkitShopRequirementType of(String name) {
        for (BukkitShopRequirementType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public ShopRequirement create(Object value) {
        switch (this) {
            case TOKEN: return new TokenShopRequirement(Long.parseLong(value.toString()));
            case GROUP: return new GroupShopRequirement((String) value);
            case PERM: return new PermissionShopRequirement((String) value);
            case MONEY: return new MoneyShopRequirement(Long.parseLong(value.toString()));
            case EXP: return new ExperienceShopRequirement(Integer.parseInt(value.toString()));
            default: return null;
        }
    }

}
