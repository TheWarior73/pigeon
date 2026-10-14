package com.github.thewarior73.pigeon.data.model

import com.github.thewarior73.pigeon.domain.data.Characteristic
import kotlinx.serialization.Serializable

@Serializable
data class BureauPoste(
    val id: String,
    val name: String,
    val city: String,
    val address: String,
    val horaire: String,
    val type: Characteristic,
    val distance: Float,
)
