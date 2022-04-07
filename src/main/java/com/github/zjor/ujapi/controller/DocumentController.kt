package com.github.zjor.ujapi.controller

import com.github.zjor.ujapi.ext.guice.Log
import com.github.zjor.ujapi.repository.MongoRepository
import org.bson.Document
import java.util.*
import javax.inject.Inject

class DocumentController @Inject constructor(private val mongoRepository: MongoRepository) {

    @Log
    fun createDocument(tenant: String, collection: String, data: Map<String, Any>): Document {
        val collectionName = "$tenant-$collection"
        return mongoRepository.save(collectionName, data)
    }

    @Log
    fun findById(tenant: String, collection: String, id: String): Optional<Document> {
        val collectionName = "$tenant-$collection"
        return mongoRepository.findById(collectionName, id);
    }

    @Log
    fun update(tenant: String, collection: String, id: String, data: Map<String, Any>): Optional<Document> {
        val collectionName = "$tenant-$collection"
        return mongoRepository.replace(collectionName, id, data)
    }

    @Log
    fun delete(tenant: String, collection: String, id: String): Optional<Document> {
        val collectionName = "$tenant-$collection"
        return mongoRepository.deleteById(collectionName, id)
    }
}