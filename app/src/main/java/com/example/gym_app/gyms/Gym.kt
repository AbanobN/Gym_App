package com.example.gym_app.gyms

import com.google.gson.annotations.SerializedName

data class Gym(
    val id : Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val location: String,
    @SerializedName("is_open")
    val isOpen: Boolean,
    var isFavorite: Boolean = false)
