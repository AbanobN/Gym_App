package com.example.gym_app.gyms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha

@Composable
fun GymsScreen(){
    LazyColumn(
        modifier = Modifier.padding(top = 40.dp, bottom = 40.dp)
    ) {
        items(listOfGyms) {gym ->
            GymItem(gym)
        }
    }
}

@Composable
fun GymIcon(vector: ImageVector, modifier: Modifier) {
    Image(imageVector = vector, contentDescription = "Gym Icon", modifier = modifier, colorFilter = ColorFilter.tint(
        Color.DarkGray))
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
fun GymItem(gym: Gym) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.padding(8.dp)){
            GymIcon(Icons.Filled.Place , Modifier.weight(0.15f))
            GymDetails(gym , Modifier.weight(0.85f))
        }
    }
}
