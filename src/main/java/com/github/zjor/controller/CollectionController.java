package com.github.zjor.controller;

import com.github.zjor.ext.spring.Tenant;
import com.github.zjor.repository.TenantMongoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
