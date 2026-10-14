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
                    parameter("q_fields", "localite")
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
        val details = fetchDetailPostes(identifiantA)
        val today = LocalDate.now()
        return details.minByOrNull { detail ->
            runCatching { abs(ChronoUnit.DAYS.between(today, LocalDate.parse(detail.date))) }
                .getOrNull() ?: Long.MAX_VALUE
        }
    }

    suspend fun fetchNextSevenDays(identifiantA: String): List<DetailPoste> {
        val today = LocalDate.now()
        val lastDay = today.plusDays(6)
        return fetchDetailPostes(identifiantA)
            .mapNotNull { detail ->
                runCatching { LocalDate.parse(detail.date) to detail }.getOrNull()
            }
            .filter { (date, _) -> !date.isBefore(today) && !date.isAfter(lastDay) }
            .sortedBy { (date, _) -> date }
            .map { (_, detail) -> detail }
    }

    private suspend fun fetchDetailPostes(identifiantA: String): List<DetailPoste> {
        if (identifiantA.isBlank()) return emptyList()

        return try {
            val response = client.get("https://data.laposte.fr/data-fair/api/v1/datasets/tjwztt6h44ve52i7fln6rbxz/lines") {
                parameter("q", identifiantA)
                parameter("sort", "date_calendrier")
                parameter("size", 100)
            }
            if (response.status.isSuccess()) {
                val result: RemoteDetailResponse = response.body()
                result.results.map { it.toDetailPoste() }
            } else {
                Log.e("LaPosteApi", "Detail HTTP Error ${response.status}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("LaPosteApi", "Detail Network Error: ${e.message}", e)
            emptyList()
        }
    }
}
