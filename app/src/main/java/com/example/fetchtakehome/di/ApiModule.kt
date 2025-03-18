package com.example.fetchtakehome.di

import com.example.fetchtakehome.api.HiringApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Reusable
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
        .build()

    @Provides
    @Reusable
    fun providesHiringApi(retrofit: Retrofit): HiringApi = retrofit.create(HiringApi::class.java)

}