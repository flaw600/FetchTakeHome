package com.example.fetchtakehome.repository

import com.example.fetchtakehome.model.HiringResponseItem

interface HiringRepository {
    suspend fun getHiringList(): List<HiringResponseItem>

}
