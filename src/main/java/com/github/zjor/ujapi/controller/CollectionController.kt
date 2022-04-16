package com.github.zjor.ujapi.controller

import com.github.zjor.ujapi.ext.guice.Log
import com.github.zjor.ujapi.repository.MongoRepository
import com.github.zjor.ujapi.repository.Paged
import org.bson.Document
import java.util.*
import javax.inject.Inject

class CollectionController @Inject constructor(private val mongoRepository: MongoRepository) {
    @Log
    fun getCollectionNames(tenant: String): List<String> {
        val prefix = "$tenant-"
        return mongoRepository.getCollectionNames(prefix)
    }

    @Log
    fun listCollection(tenant: String, collection: String, page: Int, pageSize: Int, filter: Document?): Paged<Document> {
        val collectionName = "$tenant-$collection";
        return mongoRepository.listCollection(collectionName, page, pageSize, Optional.ofNullable(filter))
    }

    @Log
    fun deleteCollection(tenant: String, collection: String) {
        val collectionName = "$tenant-$collection"
        mongoRepository.deleteCollection(collectionName)
    }
}