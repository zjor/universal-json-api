package com.github.zjor.ujapi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.zjor.ujapi.ext.jackson.ObjectIdSerializer;
import com.github.zjor.ujapi.service.JsonQueryService;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.bson.types.ObjectId;

public class JacksonModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
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

    @Provides
    @Singleton
    @Inject
    protected JsonQueryService jsonQueryService(ObjectMapper objectMapper) {
        return new JsonQueryService(objectMapper);
    }

}
