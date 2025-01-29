package com.example.gym_app

class TestGymsApiService : GymsApiService{
    override suspend fun getGyms() : List<RemoteGym>
    {
        return DummyGymsList.getDummyGymsList()
    }

    override suspend fun getGym(id:Int) : Map<String , RemoteGym>
    {

    }
}


class TestGymsDao : GymsDao {
    private var gyms = HashMap<Int,LoacalGym>()

    override suspend fun getAll() : List<LocalGym>
    {
        return gyms.values.toList()
    }

    override suspend fun addAll(gyms:List<LocalGym>)
    {
        gyms.forEach{
            this.gyms[it.id] = it
        }
    }

    override suspend fun update(localGymFavoriteState : LocalGymFavoriteState)
    {
        updateGym(localGymFavoriteState)
    }

    override suspend fun getFavoritesGyms() : List<LocalGym>
    {
        return gyms.values.toList().filter{it.isFavorite}
    }

    override suspend fun updateAll(gymsState : List<LocalGymFavoriteState>)
    {
        gymsState.forEach {
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