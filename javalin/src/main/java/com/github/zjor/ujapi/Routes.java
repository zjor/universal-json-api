package com.github.zjor.ujapi;

import com.github.zjor.ujapi.handler.DocumentController;
import com.github.zjor.ujapi.handler.IndexHandler;
import io.javalin.apibuilder.EndpointGroup;

import javax.inject.Inject;

import static io.javalin.apibuilder.ApiBuilder.get;

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
        get("/api/v1.0/c/{collection}", documentController::list);

    }
}
