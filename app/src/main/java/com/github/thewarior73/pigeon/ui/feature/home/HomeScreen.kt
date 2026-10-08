package com.github.thewarior73.pigeon.ui.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.thewarior73.pigeon.ui.theme.gray_laposte_100
import com.github.thewarior73.pigeon.ui.theme.yellow_laposte_100

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onDetailClick: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val poste by viewModel.bureauPoste.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchBureauPoste()
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    color = yellow_laposte_100
                ) {}
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    color = gray_laposte_100
                ) {}
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = yellow_laposte_100
            ) {}
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            items(items = poste) { element ->
                BureauPosteItem(
                    poste = element,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetailClick(element.name) }
                )
            }
        }
    }
}
