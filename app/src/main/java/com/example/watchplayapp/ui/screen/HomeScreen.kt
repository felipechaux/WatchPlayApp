package com.example.watchplayapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sensormanager.presentation.SensorViewModel
import com.example.watchplayapp.ui.component.RequestLocationPermission
import com.example.watchplayapp.ui.component.RequestLocationViewModel
import com.example.watchplayapp.ui.component.VideoPlayer
import com.example.watchplayapp.ui.theme.gradientEndColor
import com.example.watchplayapp.ui.theme.gradientStartColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val requestLocationViewModel: RequestLocationViewModel = hiltViewModel()
    val requestLocationState by requestLocationViewModel.state.collectAsState()

    val sensorViewModel: SensorViewModel = hiltViewModel()
    val sensorState by sensorViewModel.state.collectAsState()

    RequestLocationPermission(
        executeEvent = {
            requestLocationViewModel.getFirstLocation()
            requestLocationViewModel.requestLocationUpdates()
        },
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientStartColor,
                        gradientEndColor,
                    ),
                ),
            ),
    ) {
        VideoPlayer(
            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            isAbleToReply = requestLocationState.isAlreadyLimit,
            isAbleToPause = sensorState.isShaking,
            volumeValue = sensorState.volumeValue,
            rewindForwardValue = sensorState.rewindForwardValue
        )
    }
}
