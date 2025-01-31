package com.example.gym_app.gyms.presentation.gym_screen.viewmodel

import com.example.gym_app.gyms.domain.Gym

data class GymsScreenState(
    val gyms :List<Gym>,
    val isLoading: Boolean,
    val error: String? = null
)