package com.example.gym_app.gyms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha

@Composable
fun GymsScreen(){
    val gymsViewModel: GymsViewModel = viewModel()

    LazyColumn(
        modifier = Modifier.padding(top = 40.dp, bottom = 40.dp)
    ) {
        items(gymsViewModel.state) {gym ->
            GymItem(gym,
                onClick = { gymId ->
                    gymsViewModel.toggleFavoriteStatus(gymId)
            })
        }
    }
}

@Composable
fun GymDetails(gym: Gym,modifier: Modifier) {
    Column(modifier) {
        Text(
            text = gym.name,
            style = typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary

        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = gym.location,
                style = typography.bodyLarge,
            )
        }
    }
}

@Composable
fun GymItem(gym: Gym , onClick:(Int) -> Unit) {
    val icon = if(gym.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.padding(8.dp)){
            DefaultIcon(Icons.Filled.Place , Modifier.weight(0.15f),"Gym Icon")
            GymDetails(gym , Modifier.weight(0.70f))
            DefaultIcon(icon , Modifier.weight(0.15f) , "Favorite Icon"){
                onClick(gym.id)
            }
        }
    }
}

@Composable
fun DefaultIcon(icon : ImageVector,
                modifier: Modifier,
                contentDescription : String,
                onClick:() -> Unit = {}
    ) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier.padding(8.dp).clickable {
            onClick()
        },
        colorFilter = ColorFilter.tint(Color.DarkGray))
}

