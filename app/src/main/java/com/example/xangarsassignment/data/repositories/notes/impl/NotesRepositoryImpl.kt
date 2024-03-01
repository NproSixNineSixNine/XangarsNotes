package com.example.xangarsassignment.data.repositories.notes.impl

import com.example.xangarsassignment.models.Note
import com.example.xangarsassignment.data.local.DoodleDao
import com.example.xangarsassignment.data.local.ImageDao
import com.example.xangarsassignment.data.local.NoteDao
import com.example.xangarsassignment.data.repositories.notes.NotesRepository
import com.example.xangarsassignment.ui.screens.home.DEFAULT

class NotesRepositoryImpl(
    private val notesDao: NoteDao,
    private val doodleDao: DoodleDao,
    private val imageDao: ImageDao
) : NotesRepository {

    override suspend fun create(note: Note) {
        notesDao.insert(note = note)
    }

    override suspend fun getNote(noteId: String) = notesDao.getNoteById(noteId)

    override suspend fun delete(noteId: String) {
        notesDao.deleteNote(noteId)
        doodleDao.deleteDoodleByNote(noteId)
        imageDao.deleteImageByNote(noteId)
    }

    override fun observeNotes() = notesDao.getAllNotesByFolderId(DEFAULT)
    override fun getNotesBySearch(query: String) = notesDao.getNotesBySearch(query)

    override fun getNoteByFlow(noteId: String) = notesDao.getNoteByIdByFlow(noteId)
}