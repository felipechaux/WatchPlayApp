package com.example.watchplayapp.usecases

import android.location.Location
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class CalculateMetersByLocationUseCase @Inject constructor() {

    fun invoke(l1: Location, l2: Location): Double {
        return if (l1.hasAltitude() && l2.hasAltitude()) {
            distance(l1.latitude, l2.latitude, l1.longitude, l2.longitude, l1.altitude, l2.altitude)
        } else {
            l1.distanceTo(l2).toDouble()
        }
    }

    private fun distance(
        lat1: Double,
        lat2: Double,
        lon1: Double,
        lon2: Double,
        el1: Double,
        el2: Double,
    ): Double {
        val radiusEarth = 6371
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = sin(latDistance / 2) * sin(latDistance / 2) + cos(
            Math.toRadians(lat1),
        ) * cos(Math.toRadians(lat2)) * sin(lonDistance / 2) * sin(lonDistance / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        var distance = radiusEarth * c * 1000 // convert to meters
        val height = el1 - el2
        distance = distance.pow(2.0) + height.pow(2.0)
        return sqrt(distance)
    }
}
