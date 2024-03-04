package com.app.di

import com.app.database.AppDao
import com.app.network.ApiClientInterface
import com.app.respository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepositoryModule(
        apiClientInterface: ApiClientInterface,
        appDao: AppDao
    ): MainRepository {
        return MainRepository(apiClientInterface, appDao)
    }
}