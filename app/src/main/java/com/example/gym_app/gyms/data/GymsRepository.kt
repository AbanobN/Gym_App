package com.example.gym_app.gyms.data

import com.example.gym_app.gyms.data.remote.GymsApiService
import com.example.gym_app.gyms.GymsApplication
import com.example.gym_app.gyms.data.local.GymsDatabase
import com.example.gym_app.gyms.data.local.LocalGym
import com.example.gym_app.gyms.data.local.LocalGymFavoriteState
import com.example.gym_app.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {

    private val apiService: GymsApiService = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://gym-app-dd107-default-rtdb.firebaseio.com/")
        .build()
        .create(GymsApiService::class.java)

    private var gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())

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
        gymsDao.getAll()
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
        apiService.getGym(id).values.first()
    }

}