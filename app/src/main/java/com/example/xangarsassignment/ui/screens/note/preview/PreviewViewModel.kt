
package com.example.xangarsassignment.ui.screens.note.preview

import com.example.xangarsassignment.models.ClickableUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xangarsassignment.data.repositories.local.LocalRepository
import com.example.xangarsassignment.data.repositories.notes.NotesRepository
import getUriList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class PreviewUiState(
    val list: List<ClickableUri> = emptyList<ClickableUri>(),
    val loading: Boolean = false,
    var selection: Int = 0
)

class PreviewViewModel(
    private val localRepository: LocalRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(PreviewUiState(loading = true))
    val uiState: StateFlow<PreviewUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            localRepository.currentNote.collect { id ->
                notesRepository.getNoteByFlow(id).collect{note ->
                    note?.let {
                        _uiState.update {
                            it.copy(
                                list = note.getUriList(),
                                selection = localRepository.currentSelectedPosition.value
                            )
                        }
                    }

                }
            }
        }
    }

    fun saveCurrentDoodleId(currentDoodleId: String) {
        viewModelScope.launch {
            localRepository.saveCurrentDoodleId(currentDoodleId)
        }
    }

    fun saveCurrentImageId(currentImageId: String) {
        viewModelScope.launch {
            localRepository.saveCurrentImageId(currentImageId)
        }
    }



}