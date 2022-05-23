package dev.necron.token.common.drop.impl;

import dev.necron.token.common.drop.TokenDrop;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class EntityTokenDrop implements TokenDrop {

    private final Object dropper;
    private final long minDrop;
    private final long maxDrop;
    private final double chance;

}
