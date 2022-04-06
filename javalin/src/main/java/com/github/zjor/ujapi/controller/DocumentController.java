package com.github.zjor.ujapi.controller;

import com.github.zjor.ujapi.ext.guice.Log;
import com.github.zjor.ujapi.repository.MongoRepository;
import org.bson.Document;

import javax.inject.Inject;
import java.util.List;

public class DocumentController {

    private final MongoRepository mongoRepository;

    @Inject
    public DocumentController(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Log
    public List<Document> listCollection(String collection) {
        return mongoRepository.listCollection(collection);
    }

}
