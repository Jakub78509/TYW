package com.example.tyw.di

import com.example.tyw.databases.WeightDao
import com.example.tyw.databases.WeightDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideTaskDao(database: WeightDatabase): WeightDao {
        return database.weightDao()
    }
}