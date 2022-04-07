package com.github.zjor.ujapi.handler;

import com.github.zjor.ujapi.controller.CollectionController;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class IndexHandler implements Handler {

    private final CollectionController collectionController;

    @Inject
    public IndexHandler(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public void handle(@NotNull Context ctx) {
        ctx.json(collectionController.getCollectionNames());
    }
}
