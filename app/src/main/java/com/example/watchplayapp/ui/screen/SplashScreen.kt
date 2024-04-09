package com.example.watchplayapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.watchplayapp.R
import com.example.watchplayapp.navigation.Screens
import com.example.watchplayapp.ui.theme.Green
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000L)
        navController.navigate(Screens.Home.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize().background(Green),
        Alignment.Center,
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.lottie_ver_play),
        )
        val logoAnimationState =
            animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
        LottieAnimation(
            modifier = Modifier.fillMaxWidth(),
            composition = composition,
            progress = { logoAnimationState.progress },
            contentScale = ContentScale.Inside,
        )
    }
}
