package com.example.gym_app.gyms.presentation.gym_screen.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_app.gyms.data.di.MainDispatcher
import com.example.gym_app.gyms.domain.GetInitialGymsUseCase
import com.example.gym_app.gyms.domain.ToggleFavoriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymsViewModel @Inject constructor(
    private val initialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private var _state by mutableStateOf(
        GymsScreenState(
            gyms = emptyList(),
            isLoading = true
        )
    )
    val state: State<GymsScreenState>
    get() = derivedStateOf {_state}

    private val errorHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        getGyms()
    }

    private fun getGyms(){
        viewModelScope.launch(errorHandler + dispatcher) {
            val receivedGyms = initialGymsUseCase()
            _state = _state.copy(
                gyms = receivedGyms,
                isLoading = false
            )
        }
    }

    fun toggleFavoriteStatus(gymId: Int , oldValue: Boolean) {
        viewModelScope.launch(errorHandler + dispatcher) {
            val updatedGymsList = toggleFavoriteStateUseCase(gymId,oldValue)
            _state = _state.copy(gyms = updatedGymsList)
        }
    }


}