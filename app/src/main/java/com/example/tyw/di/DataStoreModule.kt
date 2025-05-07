package com.example.tyw.di

import android.content.Context
import com.example.tyw.datastore.DataStoreRepositoryImpl
import com.example.tyw.datastore.IDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext appContext: Context
    ): IDataStoreRepository {
        return DataStoreRepositoryImpl(appContext)
    }
}