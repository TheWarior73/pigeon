package com.github.thewarior73.pigeon.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.local.entity.BureauPosteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BureauPosteDAO {

    @Upsert
    suspend fun upsert(bureau: List<BureauPoste>)

    @Query("SELECT * FROM bureau_poste")
    fun getBureauPoste(): Flow<List<BureauPosteEntity>>
}