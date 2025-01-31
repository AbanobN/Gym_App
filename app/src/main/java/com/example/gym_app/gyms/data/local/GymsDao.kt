package com.example.gym_app.gyms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDao {
    @Query("SELECT * FROM gyms")
    suspend fun getAll():List<LocalGym>

    @Query("SELECT * FROM gyms WHERE is_favorite = 1")
    suspend fun getFavoriteGyms(): List<LocalGym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms:List<LocalGym>)

    @Update(entity = LocalGym::class)
    suspend fun update(gymFavoriteState: LocalGymFavoriteState)
    @Update(entity = LocalGym::class)
    suspend fun updateAll(gymStates: List<LocalGymFavoriteState>)
}