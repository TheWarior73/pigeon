package com.github.thewarior73.pigeon.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.github.thewarior73.pigeon.local.entity.DetailPosteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailPosteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(detail: List<DetailPosteEntity>)

    @Query("SELECT * FROM detail_poste")
    fun getDetailPoste(): Flow<List<DetailPosteEntity>>
}