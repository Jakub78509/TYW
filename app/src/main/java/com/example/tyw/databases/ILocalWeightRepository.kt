package com.example.tyw.databases

import com.example.tyw.model.Weight
import kotlinx.coroutines.flow.Flow

interface ILocalWeightRepository {
    fun getAll(userId: String): Flow<List<Weight>>
    suspend fun insert(weight: Weight): Long
    suspend fun delete(weight: Weight)
    suspend fun update(weight: Weight)
    suspend fun changeWeightState(id: Long, state: Boolean)
    suspend fun getWeight(id: Long): Weight
}
