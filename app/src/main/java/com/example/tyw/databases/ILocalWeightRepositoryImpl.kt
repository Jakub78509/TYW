package com.example.tyw.databases

import com.example.tyw.model.Weight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ILocalWeightRepositoryImpl @Inject constructor(private val dao: WeightDao) : ILocalWeightRepository {


    override fun getAll(userId: String): Flow<List<Weight>> {
        return dao.getAll(userId)
    }

    override suspend fun insert(weight: Weight): Long {
        return dao.insert(weight)
    }

    override suspend fun delete(weight: Weight) {
        dao.delete(weight)
    }

    override suspend fun update(weight: Weight) {
        dao.update(weight)
    }

    override suspend fun changeWeightState(id: Long, state: Boolean) {
        dao.changeWeightState(id, state)
    }

    override suspend fun getWeight(id: Long): Weight {
        return dao.getWeight(id)
    }
}