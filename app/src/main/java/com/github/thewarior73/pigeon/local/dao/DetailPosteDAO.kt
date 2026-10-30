package com.github.thewarior73.pigeon.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.github.thewarior73.pigeon.local.entity.DetailPosteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailPosteDAO {

    @Upsert
    suspend fun upsert(detail: List<DetailPosteEntity>)

    @Query("SELECT * FROM detail_poste WHERE id_bureau = :idBureau ORDER BY date ASC")
    fun getDetailPostes(idBureau: String): Flow<List<DetailPosteEntity>>

    @Query("DELETE FROM detail_poste WHERE date < :today")
    suspend fun deletePastDates(today: String)
}