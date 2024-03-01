package com.example.xangarsassignment.data.repositories.doodles

import com.example.xangarsassignment.models.Doodle

interface DoodlesRepository {
    suspend fun create(doodle: Doodle)
    suspend fun getDoodleById(doodleId: String): Doodle?
    suspend fun deleteDoodleById(doodleId: String)
    suspend fun deleteDoodleByNoteId(noteId: String)
}