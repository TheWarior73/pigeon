package com.github.thewarior73.pigeon.ui.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.thewarior73.pigeon.R
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.ui.theme.gray_laposte_100
import com.github.thewarior73.pigeon.ui.theme.yellow_laposte_100

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onDetailClick: (BureauPoste) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val poste by viewModel.bureauPoste.collectAsStateWithLifecycle()

    var distanceMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchBureauPoste()
    }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = yellow_laposte_100
            ) {}
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = yellow_laposte_100
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) },
                        placeholder = {
                            Text(
                                text = "Rechercher un bureau...",
                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "Rechercher",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedTextColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                            cursorColor = MaterialTheme.colorScheme.tertiary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                color = gray_laposte_100
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Box {
                        Button(onClick = { distanceMenuExpanded = true }) {
                            Text(text = viewModel.maxDistance?.let { "Distance: ${it} km" } ?: "Distance: Toutes")
                        }
                        DropdownMenu(
                            expanded = distanceMenuExpanded,
                            onDismissRequest = { distanceMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Toutes les distances") },
                                onClick = {
                                    viewModel.onMaxDistanceChanged(null)
                                    distanceMenuExpanded = false
                                }
                            )
                            listOf(20, 50, 100, 300, 500).forEach { dist ->
                                DropdownMenuItem(
                                    text = { Text("<= $dist km") },
                                    onClick = {
                                        viewModel.onMaxDistanceChanged(dist)
                                        distanceMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(items = poste) { element ->
                    BureauPosteItem(
                        poste = element,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDetailClick(element) }
                    )
                }
            }
        }
    }
}
