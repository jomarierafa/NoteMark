package com.jvrcoding.notemark.core.domain

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun clearAll()
}