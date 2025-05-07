package com.example.tyw.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val context: Context)
    : IDataStoreRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    companion object {
        private const val WEIGHT_TARGET = "WeightTarget"
        val weightTarget = doublePreferencesKey(WEIGHT_TARGET)
    }

    override suspend fun setWeight(userId: String, weight: Double) {
        context.dataStore.edit { preferences ->
            val key = getWeightTargetKey(userId)
            preferences[key] = weight
        }
    }

    override suspend fun getWeight(userId: String): Flow<Double> {
        val key = getWeightTargetKey(userId)
        return context.dataStore.data
            .map { preferences ->
                preferences[key] ?: 0.0
            }
            .catch { e ->
                e.printStackTrace()
                emit(0.0)
            }
    }

    private fun getWeightTargetKey(userId: String): Preferences.Key<Double> {
        return doublePreferencesKey("$userId.$WEIGHT_TARGET")
    }
}
