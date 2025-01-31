package com.example.gym_app.gyms.presentation.gym_details_screen.viewmodel

import com.example.gym_app.gyms.domain.Gym

data class GymDetailsScreenState(
    val gym : Gym?,
    val isLoading: Boolean,
    val error: String? = null
)