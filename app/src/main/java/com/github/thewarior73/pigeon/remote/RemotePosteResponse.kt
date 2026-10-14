package com.github.thewarior73.pigeon.remote

import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.domain.data.Characteristic
import kotlinx.serialization.Serializable

@Serializable
data class RemotePosteResponse(
    val total: Int? = null,
    val results: List<RemotePosteLine> = emptyList()
)

@Serializable
data class RemotePosteLine(
    val _id: String? = null,
    val identifiant_a: String? = null,
    val libelle_du_site: String? = null,
    val localite: String? = null,
    val adresse: String? = null,
    val horat: String? = null,
    val _geopoint: String? = null,
    val _distance: Float? = null
) {
    fun toBureauPoste(): BureauPoste {
        return BureauPoste(
            id = identifiant_a ?: _id ?: "",
            name = libelle_du_site ?: "Bureau de Poste",
            city = localite ?: "",
            address = adresse ?: "",
            horaire = horat ?: "8h-18h",
            type = Characteristic.bdp,
            distance = _distance?.let { it / 1000f } ?: 0f
        )
    }
}
