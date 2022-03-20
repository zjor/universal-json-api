package com.github.zjor.controller;

import com.github.zjor.MongoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/c")
public class CollectionController {

    private final MongoRepository mongoRepository;

    public CollectionController(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @GetMapping
    public List<String> listCollectionNames() {
        //TODO: filter collection names by <tenant>-*
        //TODO: resolve tenant by annotation
        return mongoRepository.getCollectionNames("");
    }

}
