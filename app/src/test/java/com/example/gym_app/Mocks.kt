package com.example.gym_app

import com.example.gym_app.gyms.data.local.GymsDao
import com.example.gym_app.gyms.data.local.LocalGym
import com.example.gym_app.gyms.data.local.LocalGymFavoriteState
import com.example.gym_app.gyms.data.remote.GymsApiService
import com.example.gym_app.gyms.data.remote.RemoteGym

class TestGymsApiService : GymsApiService {
    override suspend fun getGyms() : List<RemoteGym>
    {
        return DummyGymsList.getDummyGymsList()
    }

    override suspend fun getGym(id:Int) : Map<String , RemoteGym>
    {
        TODO("Not yet implemented")
    }
}


    class TestGymsDao : GymsDao {
    private var gyms = HashMap<Int,LocalGym>()

    override suspend fun getAll() : List<LocalGym>
    {
        return gyms.values.toList()
    }

        override suspend fun getFavoriteGyms(): List<LocalGym> {
            TODO("Not yet implemented")
        }

        override suspend fun addAll(gyms:List<LocalGym>)
    {
        gyms.forEach{
            this.gyms[it.id] = it
        }
    }

    override suspend fun update(gymFavoriteState : LocalGymFavoriteState)
    {
        updateGym(gymFavoriteState)
    }

    override suspend fun updateAll(gymStates : List<LocalGymFavoriteState>)
    {
        gymStates.forEach {
            updateGym(it)
        }
    }

    private fun updateGym(gymState: LocalGymFavoriteState)
    {
        val gym = this.gyms[gymState.id]
        gym?.let{
            this.gyms[gymState.id] = gym.copy(isFavorite = gymState.isFavorite)
        }
    }
}