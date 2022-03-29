package com.github.zjor.ujapi;

import com.github.zjor.ujapi.config.HandlerModule;
import com.github.zjor.ujapi.config.MongoModule;
import com.github.zjor.ujapi.handler.IndexHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.get;

public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        Injector injector = Guice.createInjector(
                new MongoModule(),
                new HandlerModule()
        );

        app.routes(() -> {
            get("/", injector.getInstance(IndexHandler.class));
        });
    }
}
