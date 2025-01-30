package com.example.gym_app.gyms

import retrofit2.http.GET

interface GymsApiService {
    @GET("gyms.json")
    suspend fun getGyms(): List<Gym>

    suspend fun getGym(id:Int) : Map<String , Gym>
}