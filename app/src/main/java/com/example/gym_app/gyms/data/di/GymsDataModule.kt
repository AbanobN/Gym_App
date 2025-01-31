package com.example.gym_app.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.example.gym_app.gyms.data.GymsRepository
import com.example.gym_app.gyms.data.local.GymsDao
import com.example.gym_app.gyms.data.local.GymsDatabase
import com.example.gym_app.gyms.data.remote.GymsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://gym-app-dd107-default-rtdb.firebaseio.com/")
            .build()
    }
    @Provides
    fun provideApiService(retrofit: Retrofit) : GymsApiService{
        return retrofit.create(GymsApiService::class.java)
    }



    @Singleton
    @Provides
    fun provideRoomDataBase(@ApplicationContext context: Context): GymsDatabase{
        return Room.databaseBuilder(
            context,
            GymsDatabase::class.java,
            "gyms_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideRoomDao(
        db: GymsDatabase
    ):GymsDao{
        return db.doa
    }

}