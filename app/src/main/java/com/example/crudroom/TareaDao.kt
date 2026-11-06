package com.example.crudroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// DAO: operaciones del CRUD
@Dao
interface TareaDao {

    // READ: devuelve un Flow, la UI se actualiza sola cuando cambia la tabla
    @Query("SELECT * FROM tareas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Tarea>>

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: Tarea)

    // UPDATE
    @Update
    suspend fun actualizar(tarea: Tarea)

    // DELETE
    @Delete
    suspend fun eliminar(tarea: Tarea)
}
