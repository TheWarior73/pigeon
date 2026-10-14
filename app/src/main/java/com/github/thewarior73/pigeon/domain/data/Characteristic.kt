package com.github.thewarior73.pigeon.domain.data

import kotlinx.serialization.Serializable

@Serializable
enum class Characteristic(val raw: String) {
    bdp("Bureau de Poste"),
    rp("Relais de Poste"),
    apc("Agence de Poste Communale")
}