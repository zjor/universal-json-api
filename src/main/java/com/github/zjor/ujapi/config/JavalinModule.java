package com.github.zjor.ujapi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.zjor.ujapi.Routes;
import com.github.zjor.ujapi.controller.CollectionController;
import com.github.zjor.ujapi.controller.DocumentController;
import com.github.zjor.ujapi.ext.jackson.ObjectIdSerializer;
import com.github.zjor.ujapi.ext.javalin.HttpRequestLogger;
import com.github.zjor.ujapi.ext.javalin.JacksonJsonMapper;
import com.github.zjor.ujapi.handler.DocumentHandler;
import com.github.zjor.ujapi.handler.IndexHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import org.bson.types.ObjectId;

public class JavalinModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CollectionController.class).asEagerSingleton();
        bind(DocumentController.class).asEagerSingleton();

        bind(IndexHandler.class).asEagerSingleton();
        bind(DocumentHandler.class).asEagerSingleton();
        bind(Routes.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    protected ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule module = new SimpleModule();
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());

        mapper.registerModule(module);
        return mapper;
    }

    private OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("My Application");
        return new OpenApiOptions(applicationInfo)
                .path("/swagger-docs")
                .swagger(new SwaggerOptions("/swagger").title("Universal JSON API :: Swagger"))
                .reDoc(new ReDocOptions("/redoc").title("Universal JSON API :: ReDoc"));
    }

    @Inject
    @Provides
    @Singleton
    protected Javalin javalin(
            ObjectMapper objectMapper,
            Routes routes) {

        var app = Javalin.create(config -> {
            config.jsonMapper(new JacksonJsonMapper(objectMapper));
            config.registerPlugin(new OpenApiPlugin(getOpenApiOptions()));
            config.requestLogger(new HttpRequestLogger());
        });

        app.routes(routes);

        return app;
    }
}