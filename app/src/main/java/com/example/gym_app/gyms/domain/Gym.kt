package com.example.gym_app.gyms.domain

data class Gym(
    val id : Int,
    val name: String,
    val location: String,
    val isOpen: Boolean,
    val isFavorite: Boolean = false
)
