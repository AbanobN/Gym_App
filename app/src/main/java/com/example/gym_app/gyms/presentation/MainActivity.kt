package com.example.gym_app.gyms.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_app.gyms.presentation.gym_details_screen.view.GymDetailsScreen
import com.example.gym_app.gyms.presentation.gym_details_screen.viewmodel.GymDetailsViewModel
import com.example.gym_app.gyms.presentation.gym_screen.view.GymsScreen
import com.example.gym_app.gyms.presentation.gym_screen.viewmodel.GymsViewModel
import com.example.gym_app.ui.theme.Gym_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gym_AppTheme{
                GymAroundApp()
            }
        }
    }
}

@Composable
fun GymAroundApp(){
   val navController = rememberNavController()
    NavHost(navController =  navController, startDestination = "gyms"){
        composable(route = "gyms") {
            val gymsViewModel:GymsViewModel = hiltViewModel()
            GymsScreen(
                state = gymsViewModel.state.value,
                onItemClick = {
                        id ->
                    navController.navigate("gyms/$id")
                },
                onFavoriteIconClick = {id , oldValue ->
                    gymsViewModel.toggleFavoriteStatus(id,oldValue)
                }
            )
        }
        composable(route = "gyms/{gym_id}", arguments = listOf(navArgument("gym_id"){
            type = NavType.IntType
        })) {
            val gymDetailsViewModel: GymDetailsViewModel = hiltViewModel()
            GymDetailsScreen(gymDetailsViewModel.state.value.gym)
        }
    }
}
