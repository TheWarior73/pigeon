package com.github.thewarior73.pigeon.data.local.entity

import android.R
import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.github.thewarior73.pigeon.data.Caracteristic

@Entity(tableName = "bureau_poste")
data class BureauPosteEntity (
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "ville") val ville: String,
    @ColumnInfo(name = "geoPoint") val geoPoint: String,
    @ColumnInfo(name = "nom") val nom: String,
    @ColumnInfo(name = "type") val type: Caracteristic, // TODO faire le type enum
){}