package dev.necron.tokens.common.storage.type.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.necron.tokens.common.config.key.ConfigKeys;
import dev.necron.tokens.common.storage.Storage;
import dev.necron.tokens.common.player.TokenPlayer;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MongoStorage implements Storage {

    private MongoCollection<Document> collection;

    @Override
    public void init() {
        MongoClient mongoClient = MongoClients.create(ConfigKeys.Settings.MONGODB_URL.getValue());
        MongoDatabase database = mongoClient.getDatabase(ConfigKeys.Settings.MONGODB_DB.getValue());
        collection = database.getCollection(ConfigKeys.Settings.MONGODB_COLLECTION.getValue());
    }

    @Override
    public void shutdown() {
        collection = null;
    }

    @Override
    public Optional<TokenPlayer> loadPlayer(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            TokenPlayer player = new TokenPlayer(uuid);
            Document document = new Document("_id", uuid.toString());
            Document result = collection.find(document).first();
            if (result == null) {
                document.append("tokens", 0);
                collection.insertOne(document);
            }else {
                player.setTokens(result.get("tokens", Number.class).longValue());
            }
            return Optional.of(player);
        }).join();
    }

    @Override
    public void savePlayer(TokenPlayer player) {
        CompletableFuture.runAsync(() -> {
            Document document = new Document("_id", player.getUuid().toString());
            document.append("tokens", player.getTokens());
            collection.updateOne(new Document("_id", player.getUuid().toString()), new Document("$set", document));
        });
    }

    @Override
    public TokenPlayer[] findLeaders(int limit) {
        return CompletableFuture.supplyAsync(() -> {
            TokenPlayer[] players = new TokenPlayer[limit];
            Iterator<Document> iterator = collection.find()
                    .sort(new Document("tokens", -1))
                    .limit(limit)
                    .iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Document document = iterator.next();
                UUID uuid = UUID.fromString(document.getString("_id"));
                TokenPlayer player = new TokenPlayer(uuid);
                player.setTokens(document.getLong("tokens"));
                players[i] = player;
                i++;
            }
            return players;
        }).join();
    }

}
