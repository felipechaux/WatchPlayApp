package com.example.requestlocationmanager

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationException(message: String) : Exception()

@Singleton
class LocationManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    suspend fun getFirstLocation(): Location? = suspendCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    continuation.resume(it)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Location", "Error getting location: ${e.message}")
                continuation.resume(null)
            }
    }

    @SuppressLint("MissingPermission")
    fun locationUpdates(intervalInMillis: Long): Flow<Location> {
        return callbackFlow {
            if (!context.checkLocationPermission()) {
                throw LocationException(context.getString(R.string.missing_location_permission))
            }
            // make the request
            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, intervalInMillis)
                .setWaitForAccurateLocation(false).build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { newLocation: Location ->
                        launch {
                            Log.e("", "Sending the location")
                            send(newLocation)
                        }
                    }
                }
            }
            fusedLocationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper(),
            )
            awaitClose {
                Log.e("", "close coroutine")
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        }
    }

    private fun Context.checkLocationPermission(): Boolean {
        val permissionGranted = PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == permissionGranted || ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == permissionGranted
    }
}
