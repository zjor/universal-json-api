package com.github.zjor.ujapi.repository;

import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MongoRepository {
    public static final String ID = "_id";
    private static final Function<String, Bson> FILTER_BY_ID =
            (id) -> new Document(ID, ObjectId.isValid(id) ? new ObjectId(id) : null);

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

    public List<Document> listCollection(String collectionName) {
        return StreamSupport.stream(getDb().getCollection(collectionName).find().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Document save(String collectionName, Map<String, Object> data) {
        Document document = (new Document(data)).append(ID, new ObjectId());
        getDb().getCollection(collectionName).insertOne(document);
        return document;
    }

    public Optional<Document> replace(String collectionName, String id, Map<String, Object> data) {
        var q = FILTER_BY_ID.apply(id);
        getDb().getCollection(collectionName).replaceOne(q, new Document(data));
        return Optional.ofNullable(getDb().getCollection(collectionName).find(q).first());
    }

    public void deleteCollection(String collectionName) {
        getDb().getCollection(collectionName).drop();
    }

    public Optional<Document> findById(String collectionName, String id) {
        return Optional.ofNullable(getDb().getCollection(collectionName).find(FILTER_BY_ID.apply(id)).first());
    }

    public Optional<Document> deleteById(String collectionName, String id) {
        return Optional.ofNullable(getDb().getCollection(collectionName).findOneAndDelete(FILTER_BY_ID.apply(id)));
    }

}
