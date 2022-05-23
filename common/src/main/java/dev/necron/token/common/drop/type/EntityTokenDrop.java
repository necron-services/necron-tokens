package dev.necron.token.common.drop.type;

import dev.necron.token.common.drop.TokenDrop;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class EntityTokenDrop implements TokenDrop {

    private final Object dropper;
    private final double chance;
    private final long minDrop;
    private final long maxDrop;

}
