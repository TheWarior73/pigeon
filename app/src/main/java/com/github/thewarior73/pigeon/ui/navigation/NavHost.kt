package com.github.thewarior73.pigeon.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.github.thewarior73.pigeon.ui.feature.home.HomeScreen

@Composable
fun NavHost() {
    val backStack = rememberNavBackStack(Destination.Home)
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Destination.Home> {
                HomeScreen(
                    onDetailClick = { poste -> backStack.add(Destination.Detail(poste = poste)) }
                )
            }
            entry<Destination.Detail> { destination ->
                Text(text = "Detail: ${destination.poste}")
            }
            entry<Destination.OpenHoursDetail> { destination ->
                Text(text = "Open Hours Detail: ${destination.poste}")
            }
        },
    )
}