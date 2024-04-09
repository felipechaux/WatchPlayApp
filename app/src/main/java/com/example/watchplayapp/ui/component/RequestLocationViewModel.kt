package com.example.watchplayapp.ui.component

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.watchplayapp.LocationManager
import com.example.watchplayapp.usecases.CalculateMetersByLocationUseCase
import com.example.watchplayapp.utils.Constants.LIMIT_METERS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestLocationViewModel @Inject constructor(
    private val locationManager: LocationManager,
    private val calculateMetersByLocation: CalculateMetersByLocationUseCase,
) : ViewModel() {

    private var currentLocation: Location? = null
    private val _state = MutableStateFlow(LocationUiState())
    val state: StateFlow<LocationUiState> = _state

    fun getFirstLocation() {
        CoroutineScope(Dispatchers.Main).launch {
            val location = locationManager.getFirstLocation()
            location?.let {
                currentLocation = it
                Log.d("FirstLocation", "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
            }
        }
    }

    fun requestLocationUpdates() {
        val locationCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        locationManager.locationUpdates(1000).onEach { newLocation ->
            Log.d(
                "requestCurrentLocation",
                "requestCurrentLocation latitude: ${newLocation.latitude} - longitude: ${newLocation.longitude}",
            )
            currentLocation?.let {
                val distance = calculateMetersByLocation.invoke(it, newLocation)
                val isApproximatelyToLimit = distance >= LIMIT_METERS
                Log.e(
                    "",
                    "distanceBetweenLocations $distance --limit: $isApproximatelyToLimit ",
                )
                if (isApproximatelyToLimit) {
                    currentLocation = newLocation
                    _state.value = LocationUiState(isAlreadyLimit = true)
                } else {
                    delay(1000)
                    setDefaultState()
                }
            }
            /***
             * Access the location only once and stop the updates immediately
             */
            // locationCoroutineScope.cancel()
        }.catch {
            Log.d("requestCurrentLocation catch", it.toString())
        }.launchIn(locationCoroutineScope)
    }

    private fun setDefaultState() {
        _state.update {
            it.copy(isAlreadyLimit = false)
        }
    }

    data class LocationUiState(
        val isAlreadyLimit: Boolean = false,
    )
}
