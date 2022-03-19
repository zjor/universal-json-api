package com.github.zjor.controller;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/v1.0/c/{collectionName}")
public class DocumentController {

    private final MongoClient client;

    public DocumentController(MongoClient client) {
        this.client = client;
    }

    @GetMapping()
    public List<Object> listCollection(@PathVariable("collectionName") String collectionName) {
        var db = client.getDatabase("storage");
        List<Object> documents = new LinkedList<>();
        db.getCollection(collectionName).find().forEach((Consumer<? super Document>) d -> documents.add(d));
        return documents;
    }

}
