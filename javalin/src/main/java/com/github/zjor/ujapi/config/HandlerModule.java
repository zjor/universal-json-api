package com.github.zjor.ujapi.config;

import com.github.zjor.ujapi.handler.IndexHandler;
import com.google.inject.AbstractModule;

public class HandlerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IndexHandler.class).asEagerSingleton();
    }
}
