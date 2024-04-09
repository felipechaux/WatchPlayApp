package com.example.watchplayapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sensormanager.presentation.SensorViewModel
import com.example.videoplayermanager.presentation.VideoPlayer
import com.example.watchplayapp.ui.component.RequestLocationPermission
import com.example.watchplayapp.ui.component.RequestLocationViewModel
import com.example.watchplayapp.ui.theme.gradientEndColor
import com.example.watchplayapp.ui.theme.gradientStartColor
import com.example.watchplayapp.utils.Constants.VIDEO_URL

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
            modifier = modifier.padding(top = 60.dp),
            url = VIDEO_URL,
            isAbleToReply = requestLocationState.isAlreadyLimit,
            isAbleToPause = sensorState.isShaking,
            volumeValue = sensorState.volumeValue,
            rewindForwardValue = sensorState.rewindForwardValue,
        )
    }
}
