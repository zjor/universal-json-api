package com.github.zjor.ujapi.handler;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TenantHeaderBeforeHandler implements Handler {

    public static final String X_TENANT_HEADER = "x-tenant";
    public static final String TENANT_ATTRIBUTE = "tenant";

    @Override
    public void handle(@NotNull Context ctx) {
        ctx.attribute(TENANT_ATTRIBUTE, Optional.ofNullable(ctx.header(X_TENANT_HEADER)).orElse("anonymous"));
    }
}
