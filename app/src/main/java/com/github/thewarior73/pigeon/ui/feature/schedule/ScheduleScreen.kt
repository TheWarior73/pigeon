package com.github.thewarior73.pigeon.ui.feature.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.data.model.DetailPoste
import com.github.thewarior73.pigeon.remote.LaPosteApi
import com.github.thewarior73.pigeon.ui.theme.yellow_laposte_100
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
	bureauPoste: BureauPoste,
	onBackClick: () -> Unit
) {
	var schedules by remember(bureauPoste.id) { mutableStateOf<List<DetailPoste>?>(null) }
	LaunchedEffect(bureauPoste.id) {
		schedules = LaPosteApi.fetchNextSevenDays(bureauPoste.id)
	}

	val today = LocalDate.now()
	val formatter = remember {
		DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRANCE)
	}
	val schedulesByDate = schedules.orEmpty().associateBy { it.date }

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Column {
						Text(bureauPoste.name)
						Text(
							text = "Horaires des 7 prochains jours",
							style = MaterialTheme.typography.labelMedium
						)
					}
				},
				navigationIcon = {
					IconButton(onClick = onBackClick) {
						Text("←", style = MaterialTheme.typography.titleLarge)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = yellow_laposte_100
				)
			)
		}
	) { innerPadding ->
		when {
			schedules == null -> Text(
				text = "Chargement des horaires…",
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding)
					.padding(24.dp),
				style = MaterialTheme.typography.bodyLarge
			)
			else -> LazyColumn(
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
				verticalArrangement = Arrangement.spacedBy(0.dp)
			) {
				items((0L..6L).toList()) { dayOffset ->
					val date = today.plusDays(dayOffset)
					val schedule = schedulesByDate[date.toString()]
					ScheduleDay(
						date = date.format(formatter),
						schedule = schedule
					)
					if (dayOffset < 6) {
						HorizontalDivider(
							modifier = Modifier.padding(horizontal = 20.dp),
							color = MaterialTheme.colorScheme.outlineVariant
						)
					}
				}
			}
		}
	}
}

@Composable
private fun ScheduleDay(date: String, schedule: DetailPoste?) {
	val morning = schedule?.plage_ouverture_AM?.trim().orEmpty()
	val afternoon = schedule?.plage_ouverture_PM?.trim().orEmpty()
	val isClosed = morning.equals("FERME", ignoreCase = true) ||
		afternoon.equals("FERME", ignoreCase = true)

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp, vertical = 16.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Text(
			text = date.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.FRANCE) else it.toString() },
			style = MaterialTheme.typography.titleMedium,
			fontWeight = FontWeight.SemiBold
		)
		when {
			schedule == null -> Text(
				text = "Horaires non communiqués",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
			isClosed -> Text(
				text = "Fermé",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.error
			)
			else -> {
				if (morning.isNotBlank()) {
					SchedulePeriod(label = "Matin", hours = morning)
				}
				if (afternoon.isNotBlank()) {
					SchedulePeriod(label = "Après-midi", hours = afternoon)
				}
				if (morning.isBlank() && afternoon.isBlank()) {
					Text(
						text = "Horaires non communiqués",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}
		}
	}
}

@Composable
private fun SchedulePeriod(label: String, hours: String) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(label, style = MaterialTheme.typography.bodyMedium)
		Text(hours, style = MaterialTheme.typography.bodyMedium)
	}
}
