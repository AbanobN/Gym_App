package com.example.gym_app

import com.example.gym_app.gyms.presentation.gym_screen.viewmodel.GymsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GymsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun loadingState_isSetCorrectly() = scope.runTest{
        val viewModel = getViewModel()
        val state = viewModel.state.value
        assert(state == GymScreenState(
            gyms = emptyList(),
            isLoading = true,
            error = null
        ))
    }

    @Test
    fun loadedContentState_isSetCorrectly() = scope.runTest{
        val viewModel = getViewModel()
        advanceUntilIdle()
        val state = viewModel.state.value
        assert(state == GymScreenState(
            gyms = DummyGymsList.getDomainDummyGymsList(),
            isLoading = false,
            error = null
        ))
    }

    private fun getViewModel(): GymsViewModel {
        val gymsRepository = GymsRepository(TestGymsApiService(),TestGymsDao())
        val getSortedGymsUseCase = GetSortedGymsUseCase(gymsRepository)
        val getInitialGymsUseCase = GetInitialGymsUseCase(gymsRepository , getSortedGymsUseCase)
        val toggleFavoriteStateUseCase = ToggleFavoriteStateUseCase(gymsRepository , getSortedGymsUseCase)

        return GymsViewModel(getInitialGymsUseCase , toggleFavoriteStateUseCase , dispatcher)
    }
}