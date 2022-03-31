package com.github.zjor.ujapi.handler;

import com.github.zjor.ujapi.repository.MongoRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Map;

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

    public void create(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        var doc = mongoRepository.save(collection, ctx.bodyAsClass(Map.class));
        ctx.json(doc);
    }

    public void delete(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        mongoRepository.deleteCollection(collection);
        ctx.status(HttpCode.NO_CONTENT);
    }

    public void getById(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        String id = ctx.pathParam("id");
        var docOpt = mongoRepository.findById(collection, id);
        if (docOpt.isEmpty()) {
            ctx.status(HttpCode.NOT_FOUND);
        } else {
            ctx.json(docOpt.get());
        }
    }

    public void updateById(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        String id = ctx.pathParam("id");
        var docOpt = mongoRepository.replace(collection, id, ctx.bodyAsClass(Map.class));
        if (docOpt.isEmpty()) {
            ctx.status(HttpCode.NOT_FOUND);
        } else {
            ctx.json(docOpt.get());
        }
    }

    public void deleteById(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        String id = ctx.pathParam("id");
        var docOpt = mongoRepository.deleteById(collection, id);
        if (docOpt.isEmpty()) {
            ctx.status(HttpCode.NOT_FOUND);
        } else {
            ctx.json(docOpt.get());
        }
    }

}
