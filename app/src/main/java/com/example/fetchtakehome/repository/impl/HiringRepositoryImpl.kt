package com.example.fetchtakehome.repository.impl

import com.example.fetchtakehome.api.HiringApi
import com.example.fetchtakehome.model.HiringResponseItem
import com.example.fetchtakehome.repository.HiringRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class HiringRepositoryImpl @Inject constructor(
    private val hiringApi: HiringApi,
    @Named("Dispatchers.IO") private val ioDispatcher: CoroutineDispatcher
) : HiringRepository {
    override suspend fun getHiringList(): List<HiringResponseItem> {
        return withContext(ioDispatcher) {
            hiringApi.getHiringList()
        }
    }
}