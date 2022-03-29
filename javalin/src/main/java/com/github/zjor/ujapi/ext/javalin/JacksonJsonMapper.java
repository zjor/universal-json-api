package com.github.zjor.ujapi.ext.javalin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.plugin.json.JsonMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JacksonJsonMapper implements JsonMapper {
    private final ObjectMapper mapper;

    public JacksonJsonMapper(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @SneakyThrows
    @NotNull
    @Override
    public String toJsonString(@NotNull Object obj) {
        return mapper.writeValueAsString(obj);
    }

    @SneakyThrows
    @NotNull
    @Override
    public InputStream toJsonStream(@NotNull Object obj) {
        return new ByteArrayInputStream(mapper.writeValueAsBytes(obj));
    }

    @SneakyThrows
    @NotNull
    @Override
    public <T> T fromJsonString(@NotNull String json, @NotNull Class<T> targetClass) {
        return mapper.readValue(json, targetClass);
    }

    @SneakyThrows
    @NotNull
    @Override
    public <T> T fromJsonStream(@NotNull InputStream json, @NotNull Class<T> targetClass) {
        return mapper.readValue(json, targetClass);
    }
}
