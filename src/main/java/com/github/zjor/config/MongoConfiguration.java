package com.github.zjor.config;

import com.github.zjor.repository.MongoRepository;
import com.github.zjor.repository.TenantMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoClient mongoClient(@Value("${mongo.uri}") String mongoUri) {
        return new MongoClient(new MongoClientURI(mongoUri));
    }

    @Bean
    public TenantMongoRepository mongoRepository(MongoClient mongoClient) {
        var repository = new MongoRepository(mongoClient);
        return new TenantMongoRepository(repository);
    }

}
