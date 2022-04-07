package com.github.zjor.ujapi;

import com.github.zjor.ujapi.handler.DocumentHandler;
import com.github.zjor.ujapi.handler.IndexHandler;
import io.javalin.apibuilder.EndpointGroup;

import javax.inject.Inject;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Routes implements EndpointGroup {

    private final IndexHandler indexHandler;
    private final DocumentHandler documentHandler;

    @Inject
    public Routes(IndexHandler indexHandler,
                  DocumentHandler documentHandler) {
        this.indexHandler = indexHandler;
        this.documentHandler = documentHandler;
    }

    @Override
    public void addEndpoints() {
        get("/", indexHandler);

        get("/api/v1.0/c", indexHandler);
        get("/api/v1.0/c/{collection}", documentHandler::list); //TODO: query collection
        post("/api/v1.0/c/{collection}", documentHandler::create);
        delete("/api/v1.0/c/{collection}", documentHandler::delete);

        get("/api/v1.0/c/{collection}/{id}", documentHandler::getById); // TODO: jq
        put("/api/v1.0/c/{collection}/{id}", documentHandler::updateById);
        delete("/api/v1.0/c/{collection}/{id}", documentHandler::deleteById);

        get("/api/v1.0/c/{collection}/{id}/<path>", documentHandler::getDocumentPart); // TODO: jq
        put("/api/v1.0/c/{collection}/{id}/<path>", documentHandler::updateDocumentPart);
        delete("/api/v1.0/c/{collection}/{id}/<path>", documentHandler::deleteDocumentPart);
    }
}
