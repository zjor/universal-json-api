package com.github.zjor.ujapi.ext.javalin;

import io.javalin.http.Context;
import io.javalin.http.RequestLogger;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class HttpRequestLogger implements RequestLogger {
    @Override
    public void handle(@NotNull Context ctx, @NotNull Float executionTimeMs) throws Exception {
        log.info("{} {}", ctx.method(), ctx.path());
    }
}
