package com.github.zjor.ujapi.config;

import com.github.zjor.ujapi.repository.MongoRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoModule extends AbstractModule {

    @Provides
    @Singleton
    protected MongoClient mongoClient() {
        var uri = System.getenv("MONGO_URI");
        return new MongoClient(new MongoClientURI(uri));
    }

    @Inject
    @Provides
    @Singleton
    protected MongoRepository mongoRepository(MongoClient mongoClient) {
        return new MongoRepository(mongoClient);
    }

}
