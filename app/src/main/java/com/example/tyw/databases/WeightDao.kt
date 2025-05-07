package com.example.tyw.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tyw.model.Weight
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {

    @Query("SELECT * FROM weight WHERE user_id = :userId")
    fun getAll(userId: String): Flow<List<Weight>>

    @Insert
    suspend fun insert(weight: Weight): Long

    @Update
    suspend fun update(weight: Weight)

    @Delete
    suspend fun delete(weight: Weight)

    @Query("UPDATE weight SET weight_state = :state WHERE id = :id")
    suspend fun changeWeightState(id: Long, state: Boolean)

    @Query("SELECT * FROM weight WHERE id = :id")
    suspend fun getWeight(id: Long): Weight

}
