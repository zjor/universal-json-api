package com.github.zjor.ujapi.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MongoClient.class).toProvider(MongoClientProvider.class).asEagerSingleton();
    }

    public static class MongoClientProvider implements Provider<MongoClient> {

        @Override
        public MongoClient get() {
            var uri = System.getenv("MONGO_URI");
            return new MongoClient(new MongoClientURI(uri));
        }
    }

}
