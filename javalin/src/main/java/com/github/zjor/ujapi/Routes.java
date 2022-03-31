package com.github.zjor.ujapi;

import com.github.zjor.ujapi.handler.DocumentController;
import com.github.zjor.ujapi.handler.IndexHandler;
import io.javalin.apibuilder.EndpointGroup;

import javax.inject.Inject;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Routes implements EndpointGroup {

    private final IndexHandler indexHandler;
    private final DocumentController documentController;

    @Inject
    public Routes(IndexHandler indexHandler,
                  DocumentController documentController) {
        this.indexHandler = indexHandler;
        this.documentController = documentController;
    }

    @Override
    public void addEndpoints() {
        get("/", indexHandler);

        get("/api/v1.0/c/{collection}", documentController::list); //TODO: query collection
        post("/api/v1.0/c/{collection}", documentController::create);
        delete("/api/v1.0/c/{collection}", documentController::delete);

        get("/api/v1.0/c/{collection}/{id}", documentController::getById); // TODO: jq
        put("/api/v1.0/c/{collection}/{id}", documentController::updateById);
        delete("/api/v1.0/c/{collection}/{id}", documentController::deleteById);

        get("/api/v1.0/c/{collection}/{id}/<path>", documentController::getDocumentPart); // TODO: jq
//        put("/api/v1.0/c/{collection}/{id}/<part>", null); // TODO
//        delete("/api/v1.0/c/{collection}/{id}/<part>", null); // TODO

        //TODO: schema support
        //TODO: log all endpoints
        //TODO: multi-tenancy manual
        //TODO: IT tests against endpoints


    }
}
