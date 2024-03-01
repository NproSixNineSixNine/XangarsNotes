
package com.example.xangarsassignment.data.repositories.notes


import com.example.xangarsassignment.models.Note
import com.example.xangarsassignment.models.NoteWithDoodleAndImage
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun create(note: Note)

    suspend fun getNote(noteId: String): NoteWithDoodleAndImage?

    suspend fun delete(noteId: String)

    fun observeNotes(): Flow<List<NoteWithDoodleAndImage>>

    fun getNotesBySearch(query:String): Flow<List<NoteWithDoodleAndImage>>

    fun getNoteByFlow(noteId: String): Flow<NoteWithDoodleAndImage?>
}