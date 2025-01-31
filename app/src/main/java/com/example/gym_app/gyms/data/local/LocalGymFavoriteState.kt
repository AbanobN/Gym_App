package com.example.gym_app.gyms.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class LocalGymFavoriteState(
    @ColumnInfo("gym_id")
    val id : Int,
    @ColumnInfo("is_favorite")
    var isFavorite: Boolean = false
)