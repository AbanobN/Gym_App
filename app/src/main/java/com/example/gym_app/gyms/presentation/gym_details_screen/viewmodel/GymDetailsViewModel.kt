package com.example.gym_app.gyms.presentation.gym_details_screen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym_app.gyms.data.GymsRepository
import com.example.gym_app.gyms.domain.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymDetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val gymRepo: GymsRepository
) : ViewModel() {

    private var _state by mutableStateOf(
        GymDetailsScreenState (
            gym = null,
            isLoading = true
        )
    )
    
    val state: State<GymDetailsScreenState>
    get() = derivedStateOf {_state}

    init {

        val gymId = stateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch {
            try {
                val gym = gymRepo.getGymFromRemoteDB(id)
                _state = _state.copy(
                    gym = Gym(id=gym.id, name=gym.name, location=gym.location, isOpen=gym.isOpen),
                    isLoading = false
                )
            } catch (e: Exception) {
                _state = _state.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "An error occurred"
                )
            }
        }
    }

}