package dev.necron.token.api.storage.impl;

import dev.necron.token.api.storage.Storage;
import dev.necron.token.api.token.TokenPlayer;

public class MongoDB implements Storage {

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public TokenPlayer loadPlayer(String name) {
        return null;
    }

    @Override
    public void savePlayer(TokenPlayer player) {

    }

}
