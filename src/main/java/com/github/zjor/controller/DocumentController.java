package com.github.zjor.controller;

import com.github.zjor.MongoRepository;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1.0/c/{collectionName}")
public class DocumentController {

    private final MongoRepository repository;

    public DocumentController(MongoRepository mongoRepository) {
        this.repository = mongoRepository;
    }

    @GetMapping
    public List<Object> listCollection(@PathVariable("collectionName") String collectionName) {
        //TODO: prefix collection name with tenant
        return repository.listCollection(collectionName);
    }

    @PostMapping
    public Document createDocument(
            @PathVariable("collectionName") String collectionName,
            @RequestBody Map<String, Object> req) {
        //TODO: prefix collection name with tenant
        return repository.save(collectionName, req);
    }

    @GetMapping("id/{id}")
    public Document getById(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id
    ) {
        //TODO: prefix collection name with tenant
        return repository.findById(collectionName, id)
                .orElseThrow(() -> new NotFoundException(collectionName + ":" + id));
    }

}
