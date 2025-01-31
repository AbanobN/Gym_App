package com.example.gym_app

import com.example.gym_app.gyms.data.GymsRepository
import com.example.gym_app.gyms.domain.GetInitialGymsUseCase
import com.example.gym_app.gyms.domain.GetSortedGymsUseCase
import com.example.gym_app.gyms.domain.ToggleFavoriteStateUseCase
import com.example.gym_app.gyms.presentation.gym_screen.viewmodel.GymsScreenState
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
        assert(state == GymsScreenState(
            gyms = emptyList(),
            isLoading = true,
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