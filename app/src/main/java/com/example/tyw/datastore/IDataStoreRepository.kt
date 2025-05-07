package com.example.tyw.datastore

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    suspend fun setWeight(userId: String, weight: Double)
    suspend fun getWeight(userId: String): Flow<Double>
}
