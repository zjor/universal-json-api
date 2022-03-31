package com.github.zjor.ujapi.handler;

import com.github.zjor.ujapi.repository.MongoRepository;
import com.github.zjor.ujapi.util.DocumentUtils;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
        mongoRepository.findById(collection, id)
                .ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void updateById(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        String id = ctx.pathParam("id");
        mongoRepository.replace(collection, id, ctx.bodyAsClass(Map.class))
                .ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void deleteById(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        String id = ctx.pathParam("id");
        mongoRepository.deleteById(collection, id)
                .ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void getDocumentPart(@NotNull Context ctx) {
        String collection = ctx.pathParam("collection");
        String id = ctx.pathParam("id");
        String path = ctx.pathParam("path");

        mongoRepository.findById(collection, id)
                .flatMap(doc -> Optional.ofNullable(DocumentUtils.getDocumentPart(doc, path)))
                .ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

}
