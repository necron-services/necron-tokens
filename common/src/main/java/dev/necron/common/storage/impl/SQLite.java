package dev.necron.common.storage.impl;

import dev.necron.common.storage.Storage;
import dev.necron.common.token.TokenPlayer;

public class SQLite implements Storage {
    @Override
    public void init() {

    }

    @Override
    public TokenPlayer loadPlayer(String name) {
        return null;
    }

    @Override
    public void savePlayer(TokenPlayer player) {

    }
}
