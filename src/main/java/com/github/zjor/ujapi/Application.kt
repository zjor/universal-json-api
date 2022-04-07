package com.github.zjor.ujapi

import com.github.zjor.ujapi.config.JavalinModule
import com.github.zjor.ujapi.config.MongoModule
import com.github.zjor.ujapi.ext.guice.LoggingModule
import com.google.inject.Guice
import io.javalin.Javalin

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        val injector = Guice.createInjector(
                LoggingModule(),
                MongoModule(),
                JavalinModule()
        )
        val port = System.getenv("PORT")?.toInt() ?: 8080
        injector.getInstance(Javalin::class.java).start(port)
    }
}