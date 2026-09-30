package com.github.thewarior73.pigeon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

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
                DetailScreen(
                    onBackClick = {backStack.removeLastOrNull()},
                    poste = destination.poste
                )
            }
            entry<Destination.OpenHoursDetail> { destination ->
                HoursDetailScreen(
                    onBackClick = {backStack.removeLastOrNull()},
                    poste = destination.poste
                )
            }
        }
    )
}