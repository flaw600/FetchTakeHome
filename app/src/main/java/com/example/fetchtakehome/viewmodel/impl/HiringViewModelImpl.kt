package com.example.fetchtakehome.viewmodel.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchtakehome.R
import com.example.fetchtakehome.usecase.GetHiringInfoByListIdUseCase
import com.example.fetchtakehome.viewmodel.HiringState
import com.example.fetchtakehome.viewmodel.HiringViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiringViewModelImpl @Inject constructor(private val getHiringListUseCase: GetHiringInfoByListIdUseCase) : ViewModel(), HiringViewModel {
    override val hiringListState by lazy { MutableStateFlow<HiringState>(HiringState.Loading) }
    override fun refresh() {
        viewModelScope.launch {
            try {
                hiringListState.value = HiringState.Loading
                val response = getHiringListUseCase()
                hiringListState.value =
                    if(response.isEmpty()) HiringState.Message(R.string.empty_message)
                    else HiringState.Success(response)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("HiringViewModelImpl", "Error fetching hiring list", e)
                hiringListState.value = HiringState.Message(R.string.error_message)
            }
        }
    }

    init {
      refresh()
    }
}