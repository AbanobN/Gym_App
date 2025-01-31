package com.example.gym_app.gyms.domain

import com.example.gym_app.gyms.data.GymsRepository

class GetInitialGymsUseCase {
    private val gymRepo = GymsRepository()
    private val sortedGymsUseCase = GetSortedGymsUseCase()

    suspend operator fun invoke() : List<Gym>{
         gymRepo.loadGyms()
        return sortedGymsUseCase()
    }

}