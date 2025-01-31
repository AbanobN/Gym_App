package com.example.gym_app.gyms.presentation.gym_screen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_app.gyms.domain.GetInitialGymsUseCase
import com.example.gym_app.gyms.domain.ToggleFavoriteStateUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

//class GymsViewModel @Inject constructor(
//    private val stateHandle: SavedStateHandle,
//    @MainDispatcher private val dispatcher: CoroutineDispatcher
//) : ViewModel() {
class GymsViewModel(
) : ViewModel() {
    private var _state by mutableStateOf(
        GymsScreenState(
            gyms = emptyList(),
            isLoading = true
        )
    )
    val state: State<GymsScreenState>
    get() = derivedStateOf { _state }

    private val initialGymsUseCase = GetInitialGymsUseCase()
    private val toggleFavoriteStateUseCase = ToggleFavoriteStateUseCase()

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
        viewModelScope.launch(errorHandler) {
            val receivedGyms = initialGymsUseCase()
            _state = _state.copy(
                gyms = receivedGyms,
                isLoading = false
            )
        }

    }

    fun toggleFavoriteStatus(gymId: Int) {
        val gyms = _state.gyms.toMutableList()
        val index = gyms.indexOfFirst { it.id == gymId }

        viewModelScope.launch {
            val updatedGymsList = toggleFavoriteStateUseCase(gymId,gyms[index].isFavorite)
            _state = _state.copy(gyms = updatedGymsList)
        }
    }

}