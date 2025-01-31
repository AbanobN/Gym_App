package com.example.gym_app.gyms

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymDetailsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel()  {
    var state = mutableStateOf<Gym?>(null)
    private var gymsApiService: GymsApiService
    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://gym-app-dd107-default-rtdb.firebaseio.com/")
            .build()
        gymsApiService = retrofit.create(GymsApiService::class.java)

        val gymId = stateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch {
            val gym = getGymFromRemoteDB(id)
            state.value = gym
        }
    }

    private suspend fun getGymFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        gymsApiService.getGym(id).values.first()
    }


}