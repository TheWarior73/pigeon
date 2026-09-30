package com.github.thewarior73.pigeon.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import java.sql.Date

@Entity(tableName = "detail_poste")
data class DetailPosteEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "id_bureau") val id_bureau: Int, //TODO FK
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "heure_ouverture_AM") val heure_ouverture_AM: String?,
    @ColumnInfo(name = "heure_fermeture_AM") val heure_fermeture_AM: String?,
    @ColumnInfo(name = "heure_ouverture_PM") val heure_ouverture_PM: String?,
    @ColumnInfo(name = "heure_fermeture_PM") val heure_fermeture_PM: String?,
    @ColumnInfo(name = "heure_fin_courier") val heure_fin_courier: String?,
    @ColumnInfo(name = "heure_fin_colis") val heure_fin_colis: String?,
    @ColumnInfo(name = "heure_fin_chronoposte") val heure_fin_chronoposte: String?,
){}
