package com.example.gym_app.gyms.domain

import com.example.gym_app.gyms.data.GymsRepository
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private val gymRepo : GymsRepository,
    private val sortedGymsUseCase: GetSortedGymsUseCase
) {
    suspend operator fun invoke() : List<Gym>{
         gymRepo.loadGyms()
        return sortedGymsUseCase()
    }

}