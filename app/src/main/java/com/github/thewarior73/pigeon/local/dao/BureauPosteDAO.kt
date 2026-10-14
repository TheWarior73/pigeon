package com.github.thewarior73.pigeon.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.thewarior73.pigeon.local.entity.BureauPosteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BureauPosteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(bureau: List<BureauPosteEntity>)

    @Query("SELECT * FROM bureau_poste")
    fun getBureauPoste(): Flow<List<BureauPosteEntity>>
}