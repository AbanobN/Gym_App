package com.example.gym_app.gyms.domain

import com.example.gym_app.gyms.data.GymsRepository
import javax.inject.Inject

class ToggleFavoriteStateUseCase @Inject constructor(
    private val gymRepo: GymsRepository,
    private val sortedGymsUseCase: GetSortedGymsUseCase
){
    suspend operator fun invoke(id: Int, oldState: Boolean) : List<Gym>{
        val newState = oldState.not()
        gymRepo.toggleFavoriteGym(id,newState)
        return sortedGymsUseCase()
    }
}