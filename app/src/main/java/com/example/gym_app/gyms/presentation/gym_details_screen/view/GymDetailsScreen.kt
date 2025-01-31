package com.example.gym_app.gyms.presentation.gym_details_screen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gym_app.gyms.domain.Gym
import com.example.gym_app.gyms.presentation.gym_screen.view.DefaultIcon
import com.example.gym_app.gyms.presentation.gym_screen.view.GymDetails


@Composable
fun GymDetailsScreen(
    item : Gym?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxSize().padding(16.dp, top = 40.dp)) {
        DefaultIcon(icon = Icons.Filled.Place,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
            contentDescription = "Location Icon")
        if (item != null) {
            GymDetails(gym = item,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
        }
        if (item != null) {
            Text(text = if(item.isOpen) "Gym is Open" else "Gym is Closed",color = if (item.isOpen) Color.Green else Color.Red)
        }
    }

}