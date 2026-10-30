package com.github.thewarior73.pigeon.repository

import android.content.Context
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.data.model.DetailPoste
import com.github.thewarior73.pigeon.local.BureauPosteDatabaseHolder
import com.github.thewarior73.pigeon.local.entity.BureauPosteEntity
import com.github.thewarior73.pigeon.local.entity.DetailPosteEntity
import com.github.thewarior73.pigeon.remote.LaPosteApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

object BureauPosteRepository {
    val bureauPostes: Flow<List<BureauPoste>> =
        BureauPosteDatabaseHolder.database?.bureauPosteDao()?.getBureauPoste()
            ?.map { list -> list.map { it.toBureauPoste() } } ?: flowOf(emptyList())

    suspend fun syncBureauxPoste(
        distance: Int? = 50,
        context: Context? = null,
        query: String? = null
    ): String? {
        try {
            val response =
                LaPosteApi.fetchPostegeoloc(distance = distance, context = context, query = query)
            val entities =
                response.results.map { BureauPosteEntity.fromBureauPoste(it.toBureauPoste()) }
            BureauPosteDatabaseHolder.database?.bureauPosteDao()?.clearAll()
            if (entities.isNotEmpty()) {
                BureauPosteDatabaseHolder.database?.bureauPosteDao()?.upsert(entities)
            }
            return response.next
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun loadNextPage(nextUrl: String): String? {
        try {
            val response = LaPosteApi.fetchNextPage(nextUrl)
            val entities =
                response.results.map { BureauPosteEntity.fromBureauPoste(it.toBureauPoste()) }
            if (entities.isNotEmpty()) {
                BureauPosteDatabaseHolder.database?.bureauPosteDao()?.upsert(entities)
            }
            return response.next
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getBureauxPoste(): Flow<List<BureauPoste>> {
        return BureauPosteDatabaseHolder.database?.bureauPosteDao()?.getBureauPoste()?.map {
            it.map { it.toBureauPoste() }
        } ?: flowOf(emptyList())
    }

    fun getDetailPostes(idBureau: String): Flow<List<DetailPoste>> {
        return BureauPosteDatabaseHolder.database?.detailPosteDao()?.getDetailPostes(idBureau)
            ?.map { list ->
                list.map { it.toDetailPoste() }
            } ?: flowOf(emptyList())
    }

    suspend fun syncDetailPostes(idBureau: String) {
        try {
            val today = java.time.LocalDate.now().toString()
            BureauPosteDatabaseHolder.database?.detailPosteDao()?.deletePastDates(today)

            val details = LaPosteApi.fetchNextSevenDays(idBureau)
            val entities = details.map { DetailPosteEntity.fromDetailPoste(it) }
            BureauPosteDatabaseHolder.database?.detailPosteDao()?.upsert(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
