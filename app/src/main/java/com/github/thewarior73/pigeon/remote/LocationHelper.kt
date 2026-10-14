package com.github.thewarior73.pigeon.remote

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

object LocationHelper {

    fun hasLocationPermission(context: Context): Boolean {
        val finePermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarsePermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return finePermission || coarsePermission
    }

    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(context: Context?): Pair<Double, Double> = withContext(Dispatchers.Main) {
        val defaultLocation = Pair(48.8566, 2.3522) // Paris default (lat, lon)
        if (context == null || !hasLocationPermission(context)) {
            return@withContext defaultLocation
        }

        return@withContext try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
                ?: return@withContext defaultLocation

            val providers = locationManager.getProviders(true)
            var bestLocation: Location? = null

            for (provider in providers) {
                val l = locationManager.getLastKnownLocation(provider) ?: continue
                if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                    bestLocation = l
                }
            }

            if (bestLocation != null) {
                return@withContext Pair(bestLocation.latitude, bestLocation.longitude)
            }

            // Request a fresh single location update if last known is null
            val freshLocation = getFreshLocation(locationManager)
            if (freshLocation != null) {
                Pair(freshLocation.latitude, freshLocation.longitude)
            } else {
                defaultLocation
            }
        } catch (e: Exception) {
            defaultLocation
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getFreshLocation(locationManager: LocationManager): Location? =
        suspendCancellableCoroutine { continuation ->
            val provider = when {
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
                else -> null
            }

            if (provider == null) {
                continuation.resume(null)
                return@suspendCancellableCoroutine
            }

            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    locationManager.removeUpdates(this)
                    if (continuation.isActive) {
                        continuation.resume(location)
                    }
                }

                @Deprecated("Deprecated in Java")
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            try {
                locationManager.requestSingleUpdate(provider, listener, null)
                continuation.invokeOnCancellation {
                    locationManager.removeUpdates(listener)
                }
            } catch (e: Exception) {
                if (continuation.isActive) continuation.resume(null)
            }
        }
}
