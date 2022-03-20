package com.github.zjor.config;

import com.github.zjor.MongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        ServerAddress address = new ServerAddress("localhost", 27017);
        MongoCredential creds = MongoCredential.createCredential("storage", "storage", "s3cr3t".toCharArray());

        return new MongoClient(address, creds, MongoClientOptions.builder().build());
    }

    @Bean
    public MongoRepository mongoRepository(MongoClient mongoClient) {
        return new MongoRepository(mongoClient);
    }

}
