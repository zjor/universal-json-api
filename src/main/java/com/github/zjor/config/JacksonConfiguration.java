package com.github.zjor.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.zjor.util.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder().serializerByType(ObjectId.class, new ObjectIdSerializer())
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
