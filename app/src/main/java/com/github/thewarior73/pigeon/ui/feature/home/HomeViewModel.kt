package com.github.thewarior73.pigeon.ui.feature.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.domain.usecase.GetBureauPoste
import com.github.thewarior73.pigeon.local.BureauPosteDatabase
import com.github.thewarior73.pigeon.remote.LaPosteApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var maxDistance by mutableStateOf<Int?>(null)
        private set

    val bureauPoste: StateFlow<List<BureauPoste>> = snapshotFlow { searchQuery }
        .combine(GetBureauPoste.invoke()) { query, list ->
            if (query.isBlank()) {
                list
            } else {
                list.filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.city.contains(query, ignoreCase = true) ||
                    it.address.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
    }

    fun onMaxDistanceChanged(distance: Int?, context: Context? = null) {
        maxDistance = distance
        fetchBureauPoste(context)
    }

    fun performApiCallWithFilters(context: Context? = null) {
        fetchBureauPoste(context)
    }

    fun fetchBureauPoste(context: Context? = null) {
        viewModelScope.launch {
            try {
                val response = LaPosteApi.fetchPostegeoloc(
                    distance = maxDistance,
                    context = context
                )
                val items = response.results.map { it.toBureauPoste() }
                if (items.isNotEmpty()) {
                    BureauPosteDatabase.updateBureauPostes(items)
                } else {
                    BureauPosteDatabase.loadInitialData()
                }
            } catch (e: Exception) {
                BureauPosteDatabase.loadInitialData()
            }
        }
    }
}
