package com.github.zjor.controller;

import com.github.zjor.ext.spring.Tenant;
import com.github.zjor.repository.TenantMongoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/c")
public class CollectionController {

    private final TenantMongoRepository repository;

    public CollectionController(TenantMongoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<String> listCollectionNames(@Tenant String tenant) {
        return repository.getCollectionNames(tenant);
    }

    @GetMapping("{collectionName}")
    public List<Object> listCollection(
            @PathVariable("collectionName") String collectionName,
            @Tenant String tenant) {
        //TODO: allow query MongoDB collection, basic filtering
        return repository.listCollection(tenant, collectionName);
    }

    @DeleteMapping("{collectionName}")
    public void deleteCollection(
            @PathVariable("collectionName") String collectionName,
            @Tenant String tenant) {
        repository.deleteCollection(tenant, collectionName);
    }

}
