package com.example.sensormanager

import com.example.sensormanager.usecase.GetShakeAccelerationUseCase
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class GetShakeAccelerationUseCaseTest {

    private lateinit var useCase: GetShakeAccelerationUseCase

    @Before
    fun setUp() {
        useCase = GetShakeAccelerationUseCase()
    }

    @Test
    fun calculateShakeAcceleration() {
        // Given
        val accelerometerValues = listOf(1.0f, 2.0f, 3.0f)
        // When
        val result = useCase.invoke(accelerometerValues)
        // Then
        assertNotNull(result)
    }
}
