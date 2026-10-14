package com.github.thewarior73.pigeon.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.github.thewarior73.pigeon.data.model.DetailPoste
import com.github.thewarior73.pigeon.local.entity.DetailPosteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailPosteDAO {

    @Upsert
    suspend fun upsert(detail: List<DetailPoste>)

    @Query("SELECT * FROM detail_poste")
    fun getDetailPoste(): Flow<List<DetailPosteEntity>>
}