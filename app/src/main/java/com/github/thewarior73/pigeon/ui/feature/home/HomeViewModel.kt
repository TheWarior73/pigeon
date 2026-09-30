package com.github.thewarior73.pigeon.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.thewarior73.pigeon.data.local.BureauPosteDatabase
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.domain.usecase.GetBureauPoste
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val bureauPoste: StateFlow<List<BureauPoste>> = GetBureauPoste.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun fetchBureauPoste() {
        viewModelScope.launch {
            BureauPosteDatabase.loadInitialData()
        }
    }
}
