package com.example.sensormanager.usecase

import  android.hardware.SensorManager
import javax.inject.Inject
import kotlin.math.sqrt

class GetShakeAccelerationUseCase @Inject constructor() {

    private var currentAcceleration = SensorManager.GRAVITY_EARTH
    private var lastAcceleration = SensorManager.GRAVITY_EARTH
    private var acceleration = 0f

    fun invoke(accelerometerValues: List<Float>): Float {
        val x = accelerometerValues[0]
        val y = accelerometerValues[1]
        val z = accelerometerValues[2]

        lastAcceleration = currentAcceleration
        currentAcceleration = sqrt(x * x + y * y + z * z)

        val delta: Float = currentAcceleration - lastAcceleration
        acceleration = acceleration * 0.9f + delta
        return acceleration
    }
}
