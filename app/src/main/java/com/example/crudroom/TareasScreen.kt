package com.example.crudroom

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TareasScreen(viewModel: TareaViewModel = viewModel()) {
    val tareas by viewModel.tareas.collectAsState()
    var texto by remember { mutableStateOf("") }
    var editando by remember { mutableStateOf<Tarea?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text("CRUD con Room", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text(if (editando == null) "Nueva tarea" else "Editar tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    if (texto.isNotBlank()) {
                        val actual = editando
                        if (actual == null) {
                            viewModel.agregar(texto.trim())                           // CREATE
                        } else {
                            viewModel.actualizar(actual.copy(titulo = texto.trim()))  // UPDATE
                            editando = null
                        }
                        texto = ""
                    }
                }
            ) { Text(if (editando == null) "Agregar" else "Guardar cambios") }

            if (editando != null) {
                OutlinedButton(onClick = { editando = null; texto = "" }) { Text("Cancelar") }
            }
        }

        Spacer(Modifier.height(16.dp))

        // READ
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(tareas, key = { it.id }) { tarea ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tarea.completada,
                        onCheckedChange = {
                            viewModel.actualizar(tarea.copy(completada = it))         // UPDATE
                        }
                    )
                    Text(tarea.titulo, modifier = Modifier.weight(1f))
                    IconButton(onClick = { editando = tarea; texto = tarea.titulo }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { viewModel.eliminar(tarea) }) {             // DELETE
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}
