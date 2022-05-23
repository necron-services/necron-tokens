package dev.necron.token.common.storage.type.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.necron.token.common.config.key.ConfigKeys;
import dev.necron.token.common.storage.Storage;
import dev.necron.token.common.token.TokenPlayer;
import org.bson.Document;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

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
        Document document = new Document("_id", uuid.toString());
        Document result = collection.find(document).first();
        if (result == null) {
            document.append("tokens", 0);
            collection.insertOne(document);
            return Optional.empty();
        }else {
            TokenPlayer player = new TokenPlayer(uuid);
            player.setTokens(result.get("tokens", Number.class).longValue());
            return Optional.of(player);
        }
    }

    @Override
    public Collection<TokenPlayer> loadPlayers(Collection<UUID> uuids) {
        Collection<TokenPlayer> players = new HashSet<>();
        for (UUID uuid : uuids) loadPlayer(uuid).ifPresent(players::add);
        return players;
    }

    @Override
    public void savePlayer(TokenPlayer player) {
        Document document = new Document("_id", player.getUuid().toString());
        document.append("tokens", player.getTokens());
        collection.updateOne(new Document("_id", player.getUuid().toString()), new Document("$set", document));
    }

    @Override
    public void savePlayers(Collection<TokenPlayer> players) {
        players.forEach(this::savePlayer);
    }

}
