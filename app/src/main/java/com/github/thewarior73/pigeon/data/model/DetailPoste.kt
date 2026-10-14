package com.github.thewarior73.pigeon.data.model

import kotlinx.serialization.Serializable

@Serializable
class DetailPoste(
    val id_bureau: String,
    val date: String,
    val heure_ouverture_AM: String?,
    val heure_fermeture_AM: String?,
    val heure_ouverture_PM: String?,
    val heure_fermeture_PM: String?,
    val heure_fin_courrier: String?,
    val heure_fin_colis: String?,
    val heure_fin_chronopost: String?
) {
}