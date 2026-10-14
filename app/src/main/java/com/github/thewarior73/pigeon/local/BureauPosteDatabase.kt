package com.github.thewarior73.pigeon.local

import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.domain.data.Characteristic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

        private val _bureauPostes = MutableStateFlow<List<BureauPoste>>(
            listOf(
                BureauPoste(
                    id = "1",
                    name = "La Poste Paris Louvre",
                    city = "Paris",
                    address = "52 Rue du Louvre, 75001 Paris",
                    horaire = "8h00 - 19h00",
                    type = Characteristic.bdp,
                    distance = 0.5f
                ),
                BureauPoste(
                    id = "2",
                    name = "La Poste Paris Chatelet",
                    city = "Paris",
                    address = "1 Quai de Gesvres, 75004 Paris",
                    horaire = "8h30 - 18h30",
                    type = Characteristic.bdp,
                    distance = 1.2f
                ),
                BureauPoste(
                    id = "3",
                    name = "Agence Communale Poste République",
                    city = "Paris",
                    address = "Place de la République, 75003 Paris",
                    horaire = "9h00 - 18h00",
                    type = Characteristic.apc,
                    distance = 2.1f
                ),
                BureauPoste(
                    id = "4",
                    name = "Relais Poste Tabac Saint-Denis",
                    city = "Saint-Denis",
                    address = "12 Avenue Jean Jaurès, 93200 Saint-Denis",
                    horaire = "7h30 - 20h00",
                    type = Characteristic.rp,
                    distance = 4.5f
                )
            )
        )
        val bureauPostes: Flow<List<BureauPoste>> = _bureauPostes.asStateFlow()

        fun get(context: Context): BureauPosteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    Room.databaseBuilder(context, BureauPosteDatabase::class.java, DB_NAME).build()
                        .also { INSTANCE = it }
                }
            }
            return INSTANCE!!
        }

        fun updateBureauPostes(newList: List<BureauPoste>) {
            _bureauPostes.update { newList }
        }

        fun loadInitialData() {
            _bureauPostes.update {
                listOf(
                    BureauPoste(
                        id = "1",
                        name = "La Poste Paris Louvre",
                        city = "Paris",
                        address = "52 Rue du Louvre, 75001 Paris",
                        horaire = "8h00 - 19h00",
                        type = Characteristic.bdp,
                        distance = 0.5f
                    ),
                    BureauPoste(
                        id = "2",
                        name = "La Poste Paris Chatelet",
                        city = "Paris",
                        address = "1 Quai de Gesvres, 75004 Paris",
                        horaire = "8h30 - 18h30",
                        type = Characteristic.bdp,
                        distance = 1.2f
                    ),
                    BureauPoste(
                        id = "3",
                        name = "Agence Communale Poste République",
                        city = "Paris",
                        address = "Place de la République, 75003 Paris",
                        horaire = "9h00 - 18h00",
                        type = Characteristic.apc,
                        distance = 2.1f
                    ),
                    BureauPoste(
                        id = "4",
                        name = "Relais Poste Tabac Saint-Denis",
                        city = "Saint-Denis",
                        address = "12 Avenue Jean Jaurès, 93200 Saint-Denis",
                        horaire = "7h30 - 20h00",
                        type = Characteristic.rp,
                        distance = 4.5f
                    )
                )
            }
        }
    }
}

object BureauPosteDatabaseHolder {
    var database: BureauPosteDatabase? = null

    fun initialize(context: Context) {
        database = BureauPosteDatabase.get(context)
    }
}