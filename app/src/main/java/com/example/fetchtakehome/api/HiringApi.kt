package com.example.fetchtakehome.api

import com.example.fetchtakehome.model.HiringResponseItem
import retrofit2.http.GET

interface HiringApi {
    @GET("/hiring.json")
    suspend fun getHiringList(): List<HiringResponseItem>
}
