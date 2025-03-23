package com.example.fetchtakehome.viewmodel

import com.example.fetchtakehome.model.HiringCandidate
import kotlinx.coroutines.flow.StateFlow

interface HiringViewModel {
    val hiringListState: StateFlow<HiringState>
    fun refresh()
}

sealed interface HiringState {
    data object Loading : HiringState
    open class Message(val messageResId: Int) : HiringState
    data class Success(val hiringListGroup: Map<Int, List<HiringCandidate>>) : HiringState
}
