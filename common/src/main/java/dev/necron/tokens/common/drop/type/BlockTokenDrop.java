package dev.necron.tokens.common.drop.type;

import dev.necron.tokens.common.drop.TokenDrop;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class BlockTokenDrop implements TokenDrop {

    private final Object dropper;
    private final byte dropperData;
    private final double chance;
    private final long minDrop;
    private final long maxDrop;

}
