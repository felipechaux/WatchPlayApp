package com.example.watchplayapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.requestlocationmanager.presentation.RequestLocationPermission
import com.example.requestlocationmanager.presentation.RequestLocationViewModel
import com.example.sensormanager.presentation.SensorViewModel
import com.example.videoplayermanager.presentation.VideoPlayer
import com.example.watchplayapp.R
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
        Text(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(vertical = 20.dp, horizontal = 20.dp),
            text = stringResource(
                id = R.string.distance_traveled,
                requestLocationState.distanceTraveled,
            ),
            style = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp,
            ),
        )
        VideoPlayer(
            modifier = modifier.padding(top = 60.dp),
            url = VIDEO_URL,
            isAbleToReply = requestLocationState.isAlreadyLimit,
            isAbleToPause = sensorState.isShaking,
            volumeValue = sensorState.volumeValue,
            rewindForwardValue = sensorState.rewindForwardValue,
        )
        Spacer(modifier = Modifier.height(0.dp))

        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(horizontal = 20.dp),
        ) {
            if (sensorState.isShaking) {
                Text(
                    text = stringResource(R.string.is_shake),
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(
                    R.string.volume_value,
                    sensorState.volumeValue,
                ),
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                ),
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(
                    R.string.rewind_forward_player,
                    sensorState.rewindForwardValue,
                ),
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                ),
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
