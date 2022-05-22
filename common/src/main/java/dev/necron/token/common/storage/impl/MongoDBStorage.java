package dev.necron.token.common.storage.impl;

import dev.necron.token.common.storage.Storage;
import dev.necron.token.common.token.TokenPlayer;

public class MongoDBStorage implements Storage {

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
