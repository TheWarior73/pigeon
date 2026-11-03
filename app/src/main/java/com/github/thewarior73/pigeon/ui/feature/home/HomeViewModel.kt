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
import com.github.thewarior73.pigeon.repository.BureauPosteRepository
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

    var isLoading by mutableStateOf(false)
        private set

    var isLoadingMore by mutableStateOf(false)
        private set

    var nextPageUrl by mutableStateOf<String?>(null)
        private set

    val hasNextPage: Boolean
        get() = !nextPageUrl.isNullOrBlank()

    val bureauPoste: StateFlow<List<BureauPoste>> = snapshotFlow { searchQuery }
        .combine(GetBureauPoste.invoke()) { query, list ->
            val filtered = if (query.isBlank()) {
                list
            } else {
                list.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.city.contains(query, ignoreCase = true) ||
                            it.address.contains(query, ignoreCase = true)
                }
            }
            filtered.distinctBy { it.id }
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
            isLoading = true
            nextPageUrl = null
            try {
                nextPageUrl = BureauPosteRepository.syncBureauxPoste(
                    distance = maxDistance,
                    context = context,
                    query = searchQuery
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadNextPage(context: Context? = null) {
        val nextUrl = nextPageUrl
        if (nextUrl.isNullOrBlank() || isLoading || isLoadingMore) return

        viewModelScope.launch {
            isLoadingMore = true
            try {
                nextPageUrl = BureauPosteRepository.loadNextPage(nextUrl)
            } catch (e: Exception) {
                // Ignore load error
            } finally {
                isLoadingMore = false
            }
        }
    }
}
