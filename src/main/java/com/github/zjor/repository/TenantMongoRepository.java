package com.github.zjor.repository;

import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TenantMongoRepository {

    private final MongoRepository repository;

    public TenantMongoRepository(MongoRepository repository) {
        this.repository = repository;
    }

    public List<String> getCollectionNames(String tenant) {
        return repository.getCollectionNames(tenant + "-");
    }

    public List<Object> listCollection(String tenant, String collectionName) {
        return repository.listCollection(getCollectionName(tenant, collectionName));
    }

    public Document save(String tenant, String collectionName, Map<String, Object> data) {
        return repository.save(getCollectionName(tenant, collectionName), data);
    }

    public void deleteCollection(String tenant, String collectionName) {
        repository.deleteCollection(getCollectionName(tenant, collectionName));
    }

    public Optional<Document> findById(String tenant, String collectionName, String id) {
        return repository.findById(getCollectionName(tenant, collectionName), id);
    }

    public Document deleteDocument(String tenant, String collectionName, String id) {
        return repository.deleteDocument(getCollectionName(tenant, collectionName), id);
    }

    private String getCollectionName(String tenant, String collectionName) {
        return tenant + "-" + collectionName;
    }
}
