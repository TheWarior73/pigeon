package com.github.thewarior73.pigeon.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {
    @Serializable
    data object Home : Destination

    @Serializable
    data class Detail(val poste: String): Destination

    @Serializable
    data class OpenHoursDetail(val poste: String) : Destination
}