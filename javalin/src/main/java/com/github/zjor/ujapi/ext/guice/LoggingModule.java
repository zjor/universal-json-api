package com.github.zjor.ujapi.ext.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class LoggingModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Log.class), new LoggingMethodInterceptor());
    }
}
