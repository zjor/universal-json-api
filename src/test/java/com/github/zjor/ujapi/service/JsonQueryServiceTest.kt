package com.github.zjor.ujapi.service

import com.github.zjor.ujapi.config.JacksonModule
import com.google.inject.Guice
import com.google.inject.Injector
import org.bson.Document
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsonQueryServiceTest {

    private val injector: Injector = Guice.createInjector(JacksonModule())
    private val jsonQueryService = injector.getInstance(JsonQueryService::class.java)

    @Test
    fun shouldQueryJsonFromList() {
        val doc = Document.parse("""
            {
                "attr": "value",
                "list": [1, 2, 3]
            }
        """.trimIndent())

        val out = jsonQueryService.query(doc, ".list[1]").get()
        assertEquals(2, out.asInt())
    }

    @Test
    fun shouldQueryJsonNodeArray() {
        val doc = Document.parse("""
            {
                "items": [
                    {
                        "id": 1,
                        "model": "x-large"
                    },
                    {
                        "id": 2,
                        "model": "medium"
                    }
                ]
            }
        """.trimIndent())

        val out = jsonQueryService.query(doc, ".items[].id").get()
        assertEquals(setOf(1, 2), out.asIterable().map { node -> node.asInt() }.toCollection(HashSet()))
    }

    @Test
    fun shouldHandleEmptyResult() {
        val doc = Document.parse("""
            {
                "attr": "value"
            }
        """.trimIndent())
        assertTrue { jsonQueryService.query(doc, ".none").isEmpty }
    }
}