package com.github.thewarior73.pigeon.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.github.thewarior73.pigeon.local.dao.BureauPosteDAO
import com.github.thewarior73.pigeon.local.dao.DetailPosteDAO
import com.github.thewarior73.pigeon.local.entity.BureauPosteEntity
import com.github.thewarior73.pigeon.local.entity.DetailPosteEntity

@Database(
    entities = [BureauPosteEntity::class, DetailPosteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BureauPosteDatabase : RoomDatabase() {

    abstract fun bureauPosteDao(): BureauPosteDAO
    abstract fun detailPosteDao(): DetailPosteDAO

    companion object {
        private const val DB_NAME = "pigeon.db"

        @Volatile
        private var INSTANCE: BureauPosteDatabase? = null

        fun get(context: Context): BureauPosteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    Room.databaseBuilder(context, BureauPosteDatabase::class.java, DB_NAME).build()
                        .also { INSTANCE = it }
                }
            }
            return INSTANCE!!
        }
    }
}

data object BureauPosteDatabaseHolder {
    var database: BureauPosteDatabase? = null

    fun initialize(context: Context) {
        database = BureauPosteDatabase.get(context);
    }
}