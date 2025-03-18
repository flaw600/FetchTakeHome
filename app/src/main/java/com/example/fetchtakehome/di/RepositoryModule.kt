package com.example.fetchtakehome.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Named("Dispatchers.IO")
    fun providesDispatchersIO(): CoroutineDispatcher = Dispatchers.IO
}
