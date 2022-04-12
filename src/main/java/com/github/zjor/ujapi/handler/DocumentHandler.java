package com.github.zjor.ujapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zjor.ujapi.controller.CollectionController;
import com.github.zjor.ujapi.controller.DocumentController;
import com.github.zjor.ujapi.service.JsonQueryService;
import com.github.zjor.ujapi.util.DocumentUtils;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class DocumentHandler {

    public final String COLLECTION_PATH_PARAM = "collection";
    public final String ID_PATH_PARAM = "id";

    public final String JQ_QUERY_PARAM = "jq";

    private final CollectionController collectionController;
    private final DocumentController documentController;

    private final ObjectMapper mapper;
    private final JsonQueryService jsonQueryService;

    @Inject
    public DocumentHandler(CollectionController collectionController,
                           DocumentController documentController,
                           ObjectMapper mapper,
                           JsonQueryService jsonQueryService) {
        this.collectionController = collectionController;
        this.documentController = documentController;
        this.mapper = mapper;
        this.jsonQueryService = jsonQueryService;
    }

    public void list(@NotNull Context ctx) {
        var collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        ctx.json(collectionController.listCollection(tenant, collection));
    }

    public void create(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        ctx.json(documentController.createDocument(tenant, collection, ctx.bodyAsClass(Map.class)));
    }

    public void delete(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);
        collectionController.deleteCollection(tenant, collection);
        ctx.status(HttpCode.NO_CONTENT);
    }

    public void getById(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        String id = ctx.pathParam(ID_PATH_PARAM);
        Optional<String> jq = Optional.ofNullable(ctx.queryParam(JQ_QUERY_PARAM));

        documentController.findById(tenant, collection, id)
                .ifPresentOrElse(
                        json -> {
                            if (jq.isPresent()) {
                                try {
                                    jsonQueryService.query(json, jq.get()).ifPresentOrElse(
                                            ctx::json,
                                            () -> ctx.status(HttpCode.NOT_FOUND)
                                    );
                                } catch (RuntimeException e) {
                                    ctx.status(HttpCode.BAD_REQUEST);
                                }
                            } else {
                                ctx.json(json);
                            }

                        },
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void updateById(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        String id = ctx.pathParam(ID_PATH_PARAM);
        documentController.update(tenant, collection, id, ctx.bodyAsClass(Map.class))
                .ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void deleteById(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        String id = ctx.pathParam(ID_PATH_PARAM);
        documentController.delete(tenant, collection, id)
                .ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void getDocumentPart(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        String id = ctx.pathParam(ID_PATH_PARAM);
        String path = ctx.pathParam("path");

        documentController.findById(tenant, collection, id)
                .flatMap(doc -> Optional.ofNullable(DocumentUtils.getDocumentPart(doc, path)))
                .ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void updateDocumentPart(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        String id = ctx.pathParam(ID_PATH_PARAM);
        String path = ctx.pathParam("path");

        documentController.findById(tenant, collection, id)
                .ifPresentOrElse(
                        doc -> {
                            try {
                                var node = mapper.readValue(ctx.bodyAsInputStream(), Object.class);
                                var updated = DocumentUtils.updateDocumentPart(doc, path, node);
                                documentController.update(tenant, collection, id, updated)
                                        .ifPresentOrElse(
                                                ctx::json,
                                                () -> ctx.status(HttpCode.NOT_FOUND));
                            } catch (IOException e) {
                                log.error("Failed to parse body: " + e.getMessage(), e);
                                ctx.status(HttpCode.BAD_REQUEST);
                            }
                        },
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

    public void deleteDocumentPart(@NotNull Context ctx) {
        String collection = ctx.pathParam(COLLECTION_PATH_PARAM);
        var tenant = (String) ctx.attribute(TenantHeaderBeforeHandler.TENANT_ATTRIBUTE);

        String id = ctx.pathParam(ID_PATH_PARAM);
        String path = ctx.pathParam("path");

        documentController.findById(tenant, collection, id)
                .ifPresentOrElse(
                        doc -> {
                            var updated = DocumentUtils.deleteDocumentPart(doc, path);
                            documentController.update(tenant, collection, id, updated)
                                    .ifPresentOrElse(
                                            ctx::json,
                                            () -> ctx.status(HttpCode.NOT_FOUND));
                        },
                        () -> ctx.status(HttpCode.NOT_FOUND));
    }

}
