package com.github.zjor.ujapi.controller;

import com.github.zjor.ujapi.ext.guice.Log;
import com.github.zjor.ujapi.repository.MongoRepository;
import org.bson.Document;

import javax.inject.Inject;
import java.util.List;

public class CollectionController {

    private final MongoRepository mongoRepository;

    @Inject
    public CollectionController(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Log
    public List<String> getCollectionNames() {
        // TODO: tenant
        return mongoRepository.getCollectionNames("");
    }

    @Log
    public List<Document> listCollection(String collection) {
        return mongoRepository.listCollection(collection);
    }

    @Log
    public void deleteCollection(String collection) {
        mongoRepository.deleteCollection(collection);
    }

}
