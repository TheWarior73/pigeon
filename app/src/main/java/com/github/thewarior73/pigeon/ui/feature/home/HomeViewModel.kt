package com.github.thewarior73.pigeon.ui.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.thewarior73.pigeon.local.BureauPosteDatabase
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.domain.usecase.GetBureauPoste
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    // Contenu de la recherche récupérable
    var searchQuery by mutableStateOf("")
        private set

    // Distance max récupérable (null = toutes les distances)
    var maxDistance by mutableStateOf<Int?>(null)
        private set

    val bureauPoste: StateFlow<List<BureauPoste>> = GetBureauPoste.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        // Vous pouvez récupérer searchQuery et maxDistance ici pour un appel API :
        // val queryToApi = searchQuery
        // val distanceToApi = maxDistance
    }

    fun onMaxDistanceChanged(distance: Int?) {
        maxDistance = distance
        // Vous pouvez récupérer searchQuery et maxDistance ici pour un appel API :
        // val queryToApi = searchQuery
        // val distanceToApi = maxDistance
    }

    // Méthode pour déclencher un appel API avec les valeurs actuelles de recherche et de distance
    fun performApiCallWithFilters() {
        val queryForApi = searchQuery
        val distanceForApi = maxDistance
        //merci l'IA
    }

    fun fetchBureauPoste() {
        viewModelScope.launch {
            BureauPosteDatabase.loadInitialData()
        }
    }
}
