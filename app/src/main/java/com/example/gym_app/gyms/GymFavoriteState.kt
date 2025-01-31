package com.example.gym_app.gyms

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class GymFavoriteState(
    @ColumnInfo("gym_id")
    val id : Int,
    @ColumnInfo("is_favorite")
    var isFavorite: Boolean = false
)