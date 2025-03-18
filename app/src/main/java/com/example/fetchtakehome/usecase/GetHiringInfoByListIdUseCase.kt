package com.example.fetchtakehome.usecase

import com.example.fetchtakehome.model.HiringResponseItem
import com.example.fetchtakehome.repository.HiringRepository
import javax.inject.Inject

class GetHiringInfoByListIdUseCase @Inject constructor(private val repository: HiringRepository) {
    suspend operator fun invoke(): Map<Int, List<HiringResponseItem>> {
        return repository.getHiringList()
            .filter { !it.name.isNullOrEmpty() }
            .groupBy { it.listId }
            .mapValues { (_, list) -> list.sortedBy { it.name } } // for some reason, sorting before grouping doesn't preserve order
            .toSortedMap()
    }
}
