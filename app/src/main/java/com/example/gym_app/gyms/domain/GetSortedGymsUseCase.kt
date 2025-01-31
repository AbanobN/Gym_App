package com.example.gym_app.gyms.domain

import com.example.gym_app.gyms.data.GymsRepository

class GetSortedGymsUseCase {
    private val gymRepo = GymsRepository()

    suspend operator fun invoke() : List<Gym>{
        return gymRepo.getGyms().sortedBy { it.name }
    }
}