package com.github.thewarior73.pigeon.remote

import com.github.thewarior73.pigeon.data.model.DetailPoste
import kotlinx.serialization.Serializable

@Serializable
data class RemoteDetailResponse(
    val results: List<RemoteDetailLine> = emptyList()
)

@Serializable
data class RemoteDetailLine(
    val identifiant: String? = null,
    val date_calendrier: String? = null,
    val plage_horaire_1: String? = null,
    val plage_horaire_2: String? = null,
    val heure_limite_depot_courrier: String? = null,
    val heure_limite_depot_colis: String? = null,
    val heure_limite_depot_chrono: String? = null
) {
    fun toDetailPoste(): DetailPoste = DetailPoste(
        id_bureau = identifiant ?: "",
        date = date_calendrier ?: "",
        plage_ouverture_AM = plage_horaire_1,
        plage_ouverture_PM = plage_horaire_2,
        heure_fin_courrier = heure_limite_depot_courrier,
        heure_fin_colis = heure_limite_depot_colis,
        heure_fin_chronopost = heure_limite_depot_chrono
    )
}