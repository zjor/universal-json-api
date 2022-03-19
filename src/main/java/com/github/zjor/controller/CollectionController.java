package com.github.zjor.controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/v1.0/c")
public class CollectionController {

    private final MongoClient client;

    public CollectionController(MongoClient client) {
        this.client = client;
    }

    @GetMapping
    public List<String> listCollectionNames() {
        MongoDatabase db = client.getDatabase("storage");

        List<String> names = new LinkedList<>();
        db.listCollectionNames().forEach((Consumer<? super String>) i -> names.add(i));
        return names;
    }

}
