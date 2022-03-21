package com.github.zjor.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MongoRepository {
    private final MongoClient client;

    public MongoRepository(MongoClient client) {
        this.client = client;
    }

    private MongoDatabase getDb() {
        return client.getDatabase("storage");
    }

    public List<String> getCollectionNames(String prefix) {
        return StreamSupport.stream(getDb().listCollectionNames().spliterator(), false)
                .filter(name -> name.startsWith(prefix))
                .map(name -> name.substring(prefix.length()))
                .collect(Collectors.toList());
    }

    public List<Object> listCollection(String collectionName) {
        return StreamSupport.stream(getDb().getCollection(collectionName).find().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Document save(String collectionName, Map<String, Object> data) {
        Document document = (new Document(data)).append("_id", new ObjectId());
        getDb().getCollection(collectionName).insertOne(document);
        return document;
    }

    public void deleteCollection(String collectionName) {
        getDb().getCollection(collectionName).drop();
    }

    public Optional<Document> findById(String collectionName, String id) {
        var q = new Document("_id", new ObjectId(id));
        return Optional.ofNullable(getDb().getCollection(collectionName).find(q).first());
    }
}
