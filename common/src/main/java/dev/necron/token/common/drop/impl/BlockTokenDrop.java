package dev.necron.token.common.drop.impl;

import dev.necron.token.common.drop.TokenDrop;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class BlockTokenDrop implements TokenDrop {

    private final Object dropper;
    private final byte dropperData;
    private final long minDrop;
    private final long maxDrop;
    private final double chance;

}
