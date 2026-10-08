package com.github.thewarior73.pigeon.local

import com.github.thewarior73.pigeon.data.model.BureauPoste
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object BureauPosteDatabase {
    private val _bureauPostes = MutableStateFlow<List<BureauPoste>>(emptyList())
    val bureauPostes: Flow<List<BureauPoste>> = _bureauPostes.asStateFlow()

    fun loadInitialData() {
        if (_bureauPostes.value.isEmpty()) {
            _bureauPostes.update {
                listOf(
                    BureauPoste("1", "Poste Centrale Paris", "Paris", "1 Rue du Louvre", "8h-18h", 18),
                    BureauPoste("2", "Poste Lyon Bellecour", "Lyon", "Place Bellecour", "8h-18h", 20),
                    BureauPoste("3", "Poste Marseille Canebière", "Marseille", "La Canebière", "8h-18h", 300)
                )
            }
        }
    }
}
