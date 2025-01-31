package com.example.gym_app.gyms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
) : ViewModel() {

    private var gymsApiService: GymsApiService

    private var gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())

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
            state = getAllGyms()
        }

    }

    private suspend fun getAllGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        }
        catch (ex:Exception){
            if(gymsDao.getAll().isEmpty())
            {
                throw Exception("Something went wrong, data wasn't found")
            }
        }
        gymsDao.getAll()
    }

    private suspend fun updateLocalDatabase() {
        val gyms = gymsApiService.getGyms()
        val favoriteGymsList  = gymsDao.getFavoriteGyms()

        gymsDao.addAll(gyms)
        gymsDao.updateAll(
            favoriteGymsList.map{
                GymFavoriteState(id = it.id , isFavorite = true)
            }
        )
    }

    var state by mutableStateOf(emptyList<Gym>())

    fun toggleFavoriteStatus(gymId: Int) {
        val gyms = state.toMutableList()
        val index = gyms.indexOfFirst { it.id == gymId }

        viewModelScope.launch {
            val updatedGymsList = toggleFavoriteGym(gymId, !gyms[index].isFavorite)
            state = updatedGymsList
        }
    }

    private suspend fun toggleFavoriteGym(gymId: Int, newFavoriteState: Boolean) = withContext(Dispatchers.IO){
        gymsDao.update(
            GymFavoriteState(
                id=gymId,
                isFavorite = newFavoriteState
            )
        )
        gymsDao.getAll()
    }

}