package com.github.zjor.ujapi.handler;

import com.mongodb.MongoClient;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IndexHandler implements Handler {

    private final MongoClient mongoClient;

    @Inject
    public IndexHandler(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void handle(@NotNull Context context) {
        List<String> collections = StreamSupport.stream(mongoClient.getDatabase("storage")
                .listCollectionNames().spliterator(), false).collect(Collectors.toList());
        context.json(collections);
    }
}
