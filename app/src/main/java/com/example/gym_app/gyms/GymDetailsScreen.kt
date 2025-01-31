package com.example.gym_app.gyms

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
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GymDetailsScreen() {
    val gymDetailsViewModel:GymDetailsViewModel = viewModel()
    val item = gymDetailsViewModel.state.value

    item?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxSize().padding(16.dp, top = 40.dp)) {
            DefaultIcon(icon = Icons.Filled.Place,
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
                contentDescription = "Location Icon")
            GymDetails(gym = item,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
            Text(text = if(item.isOpen) "Gym is Open" else "Gym is Closed",color = if (item.isOpen) Color.Green else Color.Red)
        }
    }

}