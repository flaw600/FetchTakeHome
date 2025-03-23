package com.example.fetchtakehome.usecase

import com.example.fetchtakehome.model.HiringCandidate
import com.example.fetchtakehome.repository.HiringRepository
import javax.inject.Inject

class GetHiringInfoByListIdUseCase @Inject constructor(private val repository: HiringRepository) {
    /**
     * This function takes care of the sorting and filtering specs.
     * We're converting the list into a map here because we might want to have a sticky header or collapsible lists
     * or some other UI that cares about the grouping.
     */
    suspend operator fun invoke(): Map<Int, List<HiringCandidate>> {
        return repository.getHiringList()
            .filter { !it.name.isNullOrEmpty() }
            .groupBy { it.listId } // we only care about turning this into a map if we want categorize outside of the list item (ex. sticky header)
            .mapValues { (_, list) -> list.map { HiringCandidate(it.id, it.listId, it.name!!) }.sortedBy { it.name } } // for some reason, sorting before grouping doesn't preserve order
            .toSortedMap()
    }
}
