package com.github.thewarior73.pigeon.local.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.github.thewarior73.pigeon.data.model.DetailPoste

@Entity(tableName = "detail_poste")
data class DetailPosteEntity(
    @PrimaryKey @ColumnInfo(name = "id_bureau") val id_bureau: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "heure_ouverture_AM") val heure_ouverture_AM: String?,
    @ColumnInfo(name = "heure_fermeture_AM") val heure_fermeture_AM: String?,
    @ColumnInfo(name = "heure_ouverture_PM") val heure_ouverture_PM: String?,
    @ColumnInfo(name = "heure_fermeture_PM") val heure_fermeture_PM: String?,
    @ColumnInfo(name = "heure_fin_courier") val heure_fin_courier: String?,
    @ColumnInfo(name = "heure_fin_colis") val heure_fin_colis: String?,
    @ColumnInfo(name = "heure_fin_chronoposte") val heure_fin_chronoposte: String?,
) {
    fun toDetailPoste(): DetailPoste {
        return DetailPoste(
            id_bureau,
            date,
            heure_ouverture_AM,
            heure_fermeture_AM,
            heure_ouverture_PM,
            heure_fermeture_PM,
            heure_fin_courier,
            heure_fin_colis,
            heure_fin_chronoposte
        )
    }

    companion object {
        fun fromDetailPoste(detail: DetailPoste): DetailPosteEntity {
            return DetailPosteEntity(
                detail.id_bureau,
                detail.date,
                detail.heure_ouverture_AM,
                detail.heure_fermeture_AM,
                detail.heure_ouverture_PM,
                detail.heure_fermeture_PM,
                detail.heure_fin_courrier,
                detail.heure_fin_colis,
                detail.heure_fin_chronopost
            )
        }
    }
}
