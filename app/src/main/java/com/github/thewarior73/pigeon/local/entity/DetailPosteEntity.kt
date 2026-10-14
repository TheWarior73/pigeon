package com.github.thewarior73.pigeon.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.thewarior73.pigeon.data.model.DetailPoste

@Entity(tableName = "detail_poste")
data class DetailPosteEntity(
    @PrimaryKey @ColumnInfo(name = "id_bureau") val id_bureau: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "plage_ouverture_AM") val plage_ouverture_AM: String?,
    @ColumnInfo(name = "plage_ouverture_PM") val plage_ouverture_PM: String?,
    @ColumnInfo(name = "heure_fin_courier") val heure_fin_courier: String?,
    @ColumnInfo(name = "heure_fin_colis") val heure_fin_colis: String?,
    @ColumnInfo(name = "heure_fin_chronoposte") val heure_fin_chronoposte: String?,
) {
    fun toDetailPoste(): DetailPoste {
        return DetailPoste(
            id_bureau,
            date,
            plage_ouverture_AM,
            plage_ouverture_PM,
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
                detail.plage_ouverture_AM,
                detail.plage_ouverture_PM,
                detail.heure_fin_courrier,
                detail.heure_fin_colis,
                detail.heure_fin_chronopost
            )
        }
    }
}
