package com.github.zjor.ujapi.controller

import com.github.zjor.ujapi.ext.guice.Log
import com.github.zjor.ujapi.repository.MongoRepository
import org.bson.Document
import javax.inject.Inject

class CollectionController @Inject constructor(private val mongoRepository: MongoRepository) {
    @Log
    fun getCollectionNames(tenant: String): List<String> {
        val prefix = "$tenant-"
        return mongoRepository.getCollectionNames(prefix)
    }

    @Log
    fun listCollection(tenant: String, collection: String): List<Document> {
        val collectionName = "$tenant-$collection";
        return mongoRepository.listCollection(collectionName)
    }

    @Log
    fun deleteCollection(tenant: String, collection: String) {
        val collectionName = "$tenant-$collection"
        mongoRepository.deleteCollection(collectionName)
    }
}