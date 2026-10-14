package com.github.thewarior73.pigeon.ui.navigation

import androidx.navigation3.runtime.NavKey
import com.github.thewarior73.pigeon.data.model.BureauPoste
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {
    @Serializable
    object Home : Destination

    @Serializable
    data class Detail(val bureauPoste: BureauPoste): Destination

    @Serializable
    data class OpenHoursDetail(val bureauPoste: BureauPoste) : Destination
}
