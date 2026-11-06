package com.example.crudroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareaViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getInstance(app).tareaDao()

    // READ
    val tareas: StateFlow<List<Tarea>> = dao.obtenerTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // CREATE
    fun agregar(titulo: String) = viewModelScope.launch {
        dao.insertar(Tarea(titulo = titulo))
    }

    // UPDATE
    fun actualizar(tarea: Tarea) = viewModelScope.launch {
        dao.actualizar(tarea)
    }

    // DELETE
    fun eliminar(tarea: Tarea) = viewModelScope.launch {
        dao.eliminar(tarea)
    }
}
