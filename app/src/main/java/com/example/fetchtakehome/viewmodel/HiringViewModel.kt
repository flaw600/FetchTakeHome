package com.example.fetchtakehome.viewmodel

import com.example.fetchtakehome.model.HiringResponseItem
import kotlinx.coroutines.flow.StateFlow

interface HiringViewModel {
    val hiringListState: StateFlow<HiringState>
    fun refresh()
}

sealed interface HiringState {
    data object Loading : HiringState
    open class Message(val messageResId: Int) : HiringState
    data class Success(val hiringListGroup: Map<Int, List<HiringResponseItem>>) : HiringState
}
