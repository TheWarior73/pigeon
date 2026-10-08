package com.github.thewarior73.pigeon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BureauPoste(
    val id: String,
    val name: String,
    val city: String,
    val address: String,
    val horaire: String,
    val distance: Int,
)
