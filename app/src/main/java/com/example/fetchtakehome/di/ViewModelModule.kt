package com.example.fetchtakehome.di

import com.example.fetchtakehome.repository.HiringRepository
import com.example.fetchtakehome.repository.impl.HiringRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface ViewModelModule {
    @Binds
    @ViewModelScoped
    fun bindHiringRepository(impl: HiringRepositoryImpl): HiringRepository

}