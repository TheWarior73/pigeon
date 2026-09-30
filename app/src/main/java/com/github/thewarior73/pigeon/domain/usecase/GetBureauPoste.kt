package com.github.thewarior73.pigeon.domain.usecase

import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.repository.BureauPosteRepository
import kotlinx.coroutines.flow.Flow

object GetBureauPoste {
    private val repository = BureauPosteRepository()

    operator fun invoke(): Flow<List<BureauPoste>> {
        return repository.bureauPostes
    }
}
