package com.example.gym_app

import com.example.gym_app.gyms.data.GymsRepository
import com.example.gym_app.gyms.domain.GetSortedGymsUseCase
import com.example.gym_app.gyms.domain.ToggleFavoriteStateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class ToggleFavoriteStateUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    fun toggleFavoriteState_updateFavoriteProperty() = scope.runTest{
        val gymsRepository = GymsRepository(TestGymsApiService(),TestGymsDao())
        val getSortedGymsUseCase = GetSortedGymsUseCase(gymsRepository)

        val useCaseUnderTest = ToggleFavoriteStateUseCase(gymsRepository , getSortedGymsUseCase)

        gymsRepository.loadGyms()
        advanceUntilIdle()

        val gyms = DummyGymsList.getDomainDummyGymsList()
        val gymUnderTest = gyms[0]
        val isFav = gymUnderTest.isFavorite

        val updatedGymsList = useCaseUnderTest(gymUnderTest.id, isFav)
        advanceUntilIdle()


        assert(updatedGymsList[0].isFavorite == !isFav)
    }
}