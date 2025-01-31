package com.example.gym_app.gyms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gym_app.ui.theme.Gym_AppTheme

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
            GymsScreen{id ->
                navController.navigate("gyms/$id")
            }
        }
        composable(route = "gyms/{gym_id}", arguments = listOf(navArgument("gym_id"){
            type = NavType.IntType
        })) {
            GymDetailsScreen()
        }
    }
}
