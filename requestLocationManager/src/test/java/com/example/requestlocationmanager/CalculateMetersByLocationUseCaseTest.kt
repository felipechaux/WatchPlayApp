package com.example.requestlocationmanager

import android.location.Location
import com.example.requestlocationmanager.usecase.CalculateMetersByLocationUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalculateMetersByLocationUseCaseTest {

    @Mock
    private lateinit var useCase: CalculateMetersByLocationUseCase

    @Mock
    private lateinit var location: Location

    @Test
    fun calculateMetersByLocation() {
        // Given
        val location1 = location.apply {
            latitude = 52.520008 // Berlin latitude
            longitude = 13.404954 // Berlin longitude
            altitude = 34.0 // Altitude in meters
        }

        val location2 = location.apply {
            latitude = 48.856613
            longitude = 2.352222
            altitude = 65.0
        }
        // When
        val result = useCase.invoke(location1, location2)
        // Then
        assertEquals(
            0.0,
            result,
            1.0,
        )
    }
}
