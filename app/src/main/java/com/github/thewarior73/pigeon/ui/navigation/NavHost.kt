package com.github.thewarior73.pigeon.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.github.thewarior73.pigeon.ui.feature.detail.DetailScreen
import com.github.thewarior73.pigeon.ui.feature.home.HomeScreen
import com.github.thewarior73.pigeon.ui.feature.schedule.ScheduleScreen

@Composable
fun NavHost() {
    val backStack = rememberNavBackStack(Destination.Home)
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Destination.Home> {
                HomeScreen(
                    onDetailClick = { bureauPoste -> backStack.add(Destination.Detail(bureauPoste = bureauPoste)) }
                )
            }
            entry<Destination.Detail> { destination ->
                DetailScreen(
                    bureauPoste = destination.bureauPoste,
                    onBackClick = { backStack.removeLastOrNull() },
                    onOpenHoursClick = { bureauPoste -> backStack.add(Destination.OpenHoursDetail(bureauPoste)) }
                )
            }
            entry<Destination.OpenHoursDetail> { destination ->
                ScheduleScreen(
                    bureauPoste = destination.bureauPoste,
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }
        },
    )
}
