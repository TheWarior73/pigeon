package com.github.thewarior73.pigeon.repository

import com.github.thewarior73.pigeon.local.BureauPosteDatabase
import com.github.thewarior73.pigeon.data.model.BureauPoste
import kotlinx.coroutines.flow.Flow

class BureauPosteRepository {
    val bureauPostes: Flow<List<BureauPoste>> = BureauPosteDatabase.bureauPostes

    suspend fun refreshSampleData() {
        BureauPosteDatabase.loadInitialData()
    }
}
