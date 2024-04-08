package com.example.sensormanager.presentation

import android.media.ToneGenerator.MAX_VOLUME
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sensormanager.MeasurableSensor
import com.example.sensormanager.usecase.GetShakeAccelerationUseCase
import com.example.sensormanager.utils.Constants.SHAKE_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SensorViewModel @Inject constructor(
    @Named("accelerometer")
    private val accelerometerSensor: MeasurableSensor,
    @Named("gyroscope")
    private val gyroscopeSensor: MeasurableSensor,
    private val getShakeAccelerationUseCase: GetShakeAccelerationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SensorUiState())
    val state: StateFlow<SensorUiState> = _state

    init {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener { values ->
            _state.update { it.copy(isShaking = getShakeAccelerationUseCase.invoke(values) > SHAKE_VALUE) }
        }

        gyroscopeSensor.startListening()
        gyroscopeSensor.setOnSensorValuesChangedListener { values ->
            val axisX: Float = values[0]
            val axisZ: Float = values[2]
            val volume = ((axisX + 1) / 2) * MAX_VOLUME
            Log.d("gyroscope ", " zaxis: $axisZ - xaxis $axisX")
            _state.update { it.copy(volumeValue = volume.toInt()) }
            _state.update { it.copy(rewindForwardValue = axisZ) }
        }
    }

    data class SensorUiState(
        val isShaking: Boolean = false,
        val volumeValue: Int = 0,
        val rewindForwardValue: Float = 0f,
    )
}
