package com.example.gym_app.gyms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gyms")
data class Gym(
    @PrimaryKey
    @ColumnInfo("gym_id")
    val id : Int,
    @SerializedName("gym_name")
    @ColumnInfo("gym_name")
    val name: String,
    @ColumnInfo("gym_location")
    @SerializedName("gym_location")
    val location: String,
    @SerializedName("is_open")
    val isOpen: Boolean,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean = false
)
