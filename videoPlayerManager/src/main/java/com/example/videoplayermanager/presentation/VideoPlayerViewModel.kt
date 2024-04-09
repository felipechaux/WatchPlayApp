package com.example.videoplayermanager.presentation

import android.content.Context
import android.media.AudioManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.common.Player
import com.example.videoplayermanager.utils.Constants.MAX_VOLUME
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val player: Player,
    @ApplicationContext context: Context,
) : ViewModel() {

    private val audioManager: AudioManager

    init {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        player.prepare()
        player.play()
    }

    fun setVolume(volume: Int) {
        Log.d("", "setVolume to $volume")
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            volume.coerceIn(0, MAX_VOLUME),
            0,
        )
    }
}
