package com.example.tyw.di

import com.example.tyw.databases.ILocalWeightRepository
import com.example.tyw.databases.ILocalWeightRepositoryImpl
import com.example.tyw.databases.WeightDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(dao: WeightDao): ILocalWeightRepository {
        return ILocalWeightRepositoryImpl(dao)
    }
}