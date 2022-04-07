package com.github.zjor.ujapi;

import com.github.zjor.ujapi.config.JavalinModule;
import com.github.zjor.ujapi.config.MongoModule;
import com.github.zjor.ujapi.ext.guice.LoggingModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new LoggingModule(),
                new MongoModule(),
                new JavalinModule()
        );

        injector.getInstance(Javalin.class).start(8080);
    }
}
