package com.example.xangarsassignment.ui.screens.note.note

import com.example.xangarsassignment.models.ClickableUri
import com.example.xangarsassignment.models.Note
import com.example.xangarsassignment.models.NoteWithDoodleAndImage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xangarsassignment.data.repositories.local.LocalRepository
import com.example.xangarsassignment.data.repositories.notes.NotesRepository
import com.example.xangarsassignment.ui.screens.home.DEFAULT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NoteUiState(
    val note: NoteWithDoodleAndImage = getEmptyNote(),
    val loading: Boolean = false,
)

fun getEmptyNote(id: String? = null) = NoteWithDoodleAndImage(Note(DEFAULT, "").apply {
    if (id != null) this.noteId = id
}, ArrayList(), ArrayList())


class NoteViewModel(
    private val notesRepository: NotesRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(NoteUiState(loading = true))
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            localRepository.currentNote.collect { id ->
                notesRepository.getNoteByFlow(id).collect { note ->
                    val newNote = note ?: getEmptyNote(localRepository.currentNote.value)
                    _uiState.update { it.copy(note = newNote) }
                }
            }
        }
    }

    fun setSelectedImage(pos: Int) {
        viewModelScope.launch {
            localRepository.saveSelectedPosition(pos)
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            notesRepository.create(note.apply {
                updatedOn = System.currentTimeMillis()
            })
        }

    }


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.delete(note.noteId)
            localRepository.saveCurrentNote()
        }
    }

    fun setCurrentMediaList(uriList: MutableList<ClickableUri>) {
        viewModelScope.launch {
            localRepository.saveCurrentMediaList(uriList)
        }
    }


}