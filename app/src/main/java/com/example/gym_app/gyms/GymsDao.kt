package com.example.gym_app.gyms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDao {
    @Query("SELECT * FROM gyms")
    suspend fun getAll():List<Gym>

    @Query("SELECT * FROM gyms WHERE is_favorite = 1")
    suspend fun getFavoriteGyms(): List<Gym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms:List<Gym>)

    @Update(entity = Gym::class)
    suspend fun update(gymFavoriteState: GymFavoriteState)
    @Update(entity = Gym::class)
    suspend fun updateAll(gymStates: List<GymFavoriteState>)
}