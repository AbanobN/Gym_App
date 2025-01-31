package com.example.gym_app.gyms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class GymsViewModel @Inject constructor(
//    private val stateHandle: SavedStateHandle,
//    @MainDispatcher private val dispatcher: CoroutineDispatcher
//) : ViewModel() {
class GymsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private var gymsApiService: GymsApiService

    private val errorHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://gym-app-dd107-default-rtdb.firebaseio.com/")
            .build()
        gymsApiService = retrofit.create(GymsApiService::class.java)

        getGyms()
    }


    private fun getGyms(){
        viewModelScope.launch(errorHandler) {
            val gyms = getGymsFromRemoteDB()
            state = gyms.restoreSelectedGyms()
        }

    }

    private suspend fun getGymsFromRemoteDB() = withContext(Dispatchers.IO) { gymsApiService.getGyms() }

    var state by mutableStateOf(emptyList<Gym>())

    fun toggleFavoriteStatus(gymId: Int) {
        val gyms = state.toMutableList()
        val index = gyms.indexOfFirst { it.id == gymId }
        gyms[index] = gyms[index].copy(isFavorite = !gyms[index].isFavorite)
        storeSelectedGym(gyms[index])
        state = gyms
    }

    private fun storeSelectedGym(gym: Gym) {
        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
        if(gym.isFavorite) savedHandleList.add(gym.id) else savedHandleList.remove(gym.id)
        stateHandle[FAV_IDS] = savedHandleList
    }

    private fun List<Gym>.restoreSelectedGyms() : List<Gym> {
        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS)?.let{savedIds ->
            savedIds.forEach{ gymId ->
                this.find { it.id == gymId }?.isFavorite = true
            }
        }

        return this
    }

    companion object{
        const val FAV_IDS = "favoriteGymsIDs"
    }
}