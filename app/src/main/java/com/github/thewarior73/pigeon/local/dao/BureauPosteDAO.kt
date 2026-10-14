package com.github.thewarior73.pigeon.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.github.thewarior73.pigeon.local.entity.BureauPosteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BureauPosteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(bureau: List<BureauPosteEntity>)

    @Query("SELECT * FROM bureau_poste")
    fun getBureauPoste(): Flow<List<BureauPosteEntity>>
}