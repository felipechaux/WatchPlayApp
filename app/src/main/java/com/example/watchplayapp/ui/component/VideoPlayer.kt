package com.example.watchplayapp.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import com.example.watchplayapp.utils.Constants.REWIND_FORWARD_SECONDS

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    isAbleToReply: Boolean = false,
    isAbleToPause: Boolean = false,
    volumeValue: Int = 0,
    rewindForwardValue: Float = 0f,
    url: String,
) {
    val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
    videoPlayerViewModel.setVolume(volumeValue)

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(5),
                )
                .aspectRatio(16 / 9f),
            factory = { context ->
                PlayerView(context).also {
                    it.player = videoPlayerViewModel.player
                    it.player?.setMediaItem(MediaItem.fromUri(url))
                }
            },
            update = {
                val forward = it.player?.currentPosition
                if (rewindForwardValue > 0) {
                    forward?.plus(REWIND_FORWARD_SECONDS)
                } else if (rewindForwardValue < 0) {
                    forward?.minus(REWIND_FORWARD_SECONDS)
                }
                if (isAbleToReply) {
                    Log.d("", "isAbleToReply!!!!!")
                    it.player = videoPlayerViewModel.player
                    it.player?.setMediaItem(MediaItem.fromUri(url))
                }
                if (isAbleToPause) {
                    Log.d("", "isAbleToPause!!!!!")
                    it.player?.pause()
                }
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                    }

                    else -> Unit
                }
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
