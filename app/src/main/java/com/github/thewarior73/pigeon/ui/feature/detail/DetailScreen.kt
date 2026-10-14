package com.github.thewarior73.pigeon.ui.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.thewarior73.pigeon.R
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.data.model.DetailPoste
import com.github.thewarior73.pigeon.remote.LaPosteApi
import com.github.thewarior73.pigeon.ui.theme.blue_laposte
import com.github.thewarior73.pigeon.ui.theme.gray_laposte_500
import com.github.thewarior73.pigeon.ui.theme.yellow_laposte_100
import com.github.thewarior73.pigeon.ui.theme.yellow_laposte_200
import java.time.LocalTime

private fun isCurrentlyOpen(horaire: String): Boolean {
    return try {
        val now = LocalTime.now()
        val regex = Regex("""(\d{1,2})h?(\d{2})?\s*-\s*(\d{1,2})h?(\d{2})?""")
        val matches = regex.findAll(horaire).toList()
        for (match in matches) {
            val startHour = match.groupValues[1].toInt()
            val startMin = match.groupValues[2].ifEmpty { "0" }.toInt()
            val endHour = match.groupValues[3].toInt()
            val endMin = match.groupValues[4].ifEmpty { "0" }.toInt()

            val start = LocalTime.of(startHour, startMin)
            val end = LocalTime.of(endHour, endMin)

            if (!now.isBefore(start) && !now.isAfter(end)) {
                return true
            }
        }
        false
    } catch (e: Exception) {
        false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    bureauPoste: BureauPoste,
    detail: DetailPoste? = null,
    onBackClick: () -> Unit,
    onOpenHoursClick: (BureauPoste) -> Unit = {}
) {
    var loadedDetail by remember(bureauPoste.id) { mutableStateOf(detail) }
    LaunchedEffect(bureauPoste.id) {
        loadedDetail = LaPosteApi.fetchDetailPoste(bureauPoste.id)
    }
    val isOpen = isCurrentlyOpen(bureauPoste.horaire)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = bureauPoste.name,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text(
                            text = "←",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = yellow_laposte_100,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                    navigationIconContentColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Maison / Ville",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = bureauPoste.city,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${bureauPoste.distance} km",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.alternative_distance),
                        contentDescription = "Graduation / Distance",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = yellow_laposte_200,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_alarm_24),
                                contentDescription = "Horaires",
                                tint = blue_laposte,
                                modifier = Modifier.size(24.dp)
                            )
                            Surface(
                                color = if (isOpen) Color(0xFF2E7D32) else Color(0xFFC62828),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = if (isOpen) "OUVERT" else "FERMÉ",
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.padding(start = 32.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "• Matin : ${loadedDetail?.plage_ouverture_AM ?: "—"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = blue_laposte
                            )
                            Text(
                                text = "• Après-midi : ${loadedDetail?.plage_ouverture_PM ?: "—"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = blue_laposte
                            )
                        }
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = gray_laposte_500,
                thickness = 2.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Heures limites de dépôt",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "• Courrier :",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        loadedDetail?.heure_fin_courrier?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "• Colis :",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        loadedDetail?.heure_fin_colis?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "• Chronopost :",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        loadedDetail?.heure_fin_chronopost?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }

            Card(
                onClick = { onOpenHoursClick(bureauPoste) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = blue_laposte,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Horaires sur les 7 prochains jours",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "Voir les horaires",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }


        }
    }
}
