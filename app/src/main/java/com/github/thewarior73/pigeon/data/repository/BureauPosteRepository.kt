package com.github.thewarior73.pigeon.data.repository

import com.github.thewarior73.pigeon.data.local.BureauPosteDatabase
import com.github.thewarior73.pigeon.data.model.BureauPoste
import kotlinx.coroutines.flow.Flow

class BureauPosteRepository {
    val bureauPostes: Flow<List<BureauPoste>> = BureauPosteDatabase.bureauPostes

    suspend fun refreshSampleData() {
        BureauPosteDatabase.loadInitialData()
    }
}
