package com.example.sensormanager.usecase

import android.media.ToneGenerator
import javax.inject.Inject

class GetVolumeUseCase @Inject constructor() {
    fun invoke(axisX: Float): Int {
        val volume = ((axisX + 1) / 2) * ToneGenerator.MAX_VOLUME
        return volume.toInt()
    }
}
