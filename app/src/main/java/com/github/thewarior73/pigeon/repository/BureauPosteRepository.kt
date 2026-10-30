package com.github.thewarior73.pigeon.repository

import com.github.thewarior73.pigeon.local.BureauPosteDatabase
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.local.BureauPosteDatabaseHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

object BureauPosteRepository {
    val bureauPostes: Flow<List<BureauPoste>> = BureauPosteDatabase.bureauPostes

    suspend fun refreshSampleData() {
        BureauPosteDatabase.loadInitialData()
    }

    suspend fun setBureauPoste() {

    }

    fun getBureauxPoste(): Flow<List<BureauPoste>> {
        return BureauPosteDatabaseHolder.database?.bureauPosteDao()?.getBureauPoste()?.map {
            it.map { it.toBureauPoste() } } ?: flowOf(emptyList())
    }
}
