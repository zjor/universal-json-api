package com.github.zjor.ujapi.handler;

import com.github.zjor.ujapi.repository.MongoRepository;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class DocumentController {

    private final MongoRepository mongoRepository;

    @Inject
    public DocumentController(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    public void list(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        ctx.json(mongoRepository.listCollection(collection));
    }

}
