package com.github.thewarior73.pigeon.remote

import android.content.Context
import android.util.Log
import com.github.thewarior73.pigeon.data.model.DetailPoste
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

object LaPosteApi {
    private val client by lazy { HttpClientFactory.create() }

    suspend fun fetchPostegeoloc(
        distance: Int?,
        context: Context? = null,
        latitude: Double? = null,
        longitude: Double? = null,
        query: String? = null
    ): RemotePosteResponse {
        val (lat, lon) = if (latitude != null && longitude != null) {
            Pair(latitude, longitude)
        } else {
            LocationHelper.getUserLocation(context)
        }

        val distanceInMeters = (distance ?: 50) * 1000

        return try {
            val response = client.get("https://data.laposte.fr/data-fair/api/v1/datasets/laposte-poincont2/lines") {
                parameter("geo_distance", "$lon:$lat:$distanceInMeters")
                if (!query.isNullOrBlank()) {
                    parameter("q", query)
                }
            }
            if (response.status.isSuccess()) {
                response.body()
            } else {
                Log.e("LaPosteApi", "HTTP Error ${response.status}")
                RemotePosteResponse()
            }
        } catch (e: Exception) {
            Log.e("LaPosteApi", "Network Error: ${e.message}", e)
            RemotePosteResponse()
        }
    }

    suspend fun fetchDetailPoste(identifiantA: String): DetailPoste? {
        if (identifiantA.isBlank()) return null

        return try {
            val response = client.get("https://data.laposte.fr/data-fair/api/v1/datasets/tjwztt6h44ve52i7fln6rbxz/lines") {
                parameter("q", identifiantA)
                parameter("size", 100)
            }
            if (response.status.isSuccess()) {
                val result: RemoteDetailResponse = response.body()
                val today = LocalDate.now()
                result.results.minByOrNull { line ->
                    line.date_calendrier?.let { date ->
                        runCatching { abs(ChronoUnit.DAYS.between(today, LocalDate.parse(date))) }
                            .getOrNull()
                    } ?: Long.MAX_VALUE
                }?.toDetailPoste()
            } else {
                Log.e("LaPosteApi", "Detail HTTP Error ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.e("LaPosteApi", "Detail Network Error: ${e.message}", e)
            null
        }
    }
}
