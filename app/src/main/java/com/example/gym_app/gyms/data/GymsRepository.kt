package com.example.gym_app.gyms.data

import android.util.Log
import com.example.gym_app.gyms.data.local.GymsDao
import com.example.gym_app.gyms.data.local.LocalGym
import com.example.gym_app.gyms.data.local.LocalGymFavoriteState
import com.example.gym_app.gyms.data.remote.GymsApiService
import com.example.gym_app.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService: GymsApiService,
    private var gymsDao:GymsDao
) {

    suspend fun toggleFavoriteGym(gymId: Int, state: Boolean) = withContext(
        Dispatchers.IO){
        gymsDao.update(
            LocalGymFavoriteState(
                id=gymId,
                isFavorite = state
            )
        )
        gymsDao.getAll()
    }

    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        }
        catch (ex:Exception){
            if(gymsDao.getAll().isEmpty())
            {
                throw Exception("Something went wrong, data wasn't found")
            }
        }
    }

    suspend fun getGyms():List<Gym>{
        return withContext(Dispatchers.IO){
            return@withContext gymsDao.getAll().map {
                Gym(id=it.id,name=it.name, location = it.location, isOpen = it.isOpen , isFavorite = it.isFavorite)
            }
        }
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favoriteGymsList  = gymsDao.getFavoriteGyms()
        gymsDao.addAll(
            gyms.map{
            LocalGym(id=it.id,name=it.name, location = it.location, isOpen = it.isOpen)
        })
        gymsDao.updateAll(
            favoriteGymsList.map{
                LocalGymFavoriteState(id = it.id , isFavorite = true)
            }
        )
    }

    suspend fun getGymFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGym(id).values.firstOrNull() ?: throw Exception("Gym not found")
    }

}