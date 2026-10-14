package com.github.thewarior73.pigeon.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.domain.data.Characteristic

@Entity(tableName = "bureau_poste")
data class BureauPosteEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "nom") val nom: String,
    @ColumnInfo(name = "ville") val ville: String,
    @ColumnInfo(name = "adresse") val adresse: String,
    @ColumnInfo(name = "horaire") val horaire: String,
    @ColumnInfo(name = "type") val type: Characteristic,
    @ColumnInfo(name = "distance") val distance: Float,
) {

    fun toBureauPoste(): BureauPoste {
        return BureauPoste(id, nom, ville, adresse, horaire, type, distance)
    }

    companion object {
        fun fromBureauPoste(bureau: BureauPoste): BureauPosteEntity {
            return BureauPosteEntity(
                bureau.id,
                bureau.name,
                bureau.city,
                bureau.address,
                bureau.horaire,
                bureau.type,
                bureau.distance
            )
        }
    }
}