package com.github.zjor.ujapi.ext.javalin;

import io.javalin.http.Context;
import io.javalin.http.RequestLogger;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class HttpRequestLogger implements RequestLogger {
    @Override
    public void handle(@NotNull Context ctx, @NotNull Float executionTimeMs) throws Exception {
        StringBuilder logMessage = new StringBuilder(ctx.method())
                .append(' ')
                .append(ctx.path());
        if (ctx.queryString() != null) {
            logMessage.append('?').append(ctx.queryString());
        }

        log.info("{}", logMessage);
    }
}
