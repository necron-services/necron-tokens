package dev.necron.tokens.common.leaderboard.leader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class Leader {

    private final UUID uuid;
    private final String name;
    private final long tokens;

}
