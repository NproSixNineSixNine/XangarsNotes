package com.example.xangarsassignment.data.repositories.local.impl

import com.example.xangarsassignment.models.ClickableUri
import com.example.xangarsassignment.data.repositories.local.LocalRepository

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*


class LocalRepositoryImpl : LocalRepository {

    override val currentNote = MutableStateFlow("")
    override suspend fun saveCurrentNote(currentNote: String) {
        this.currentNote.emit(currentNote)
    }

    override val currentDoodleId = MutableStateFlow(UUID.randomUUID().toString())
    override suspend fun saveCurrentDoodleId(currentDoodleId: String) {
        this.currentDoodleId.emit(currentDoodleId)
    }


    override val currentImageId = MutableStateFlow(UUID.randomUUID().toString())
    override suspend fun saveCurrentImageId(currentImageId: String) {
        this.currentImageId.emit(currentImageId)
    }



    override val currentMediaList = MutableStateFlow(emptyList<ClickableUri>())
    override suspend fun saveCurrentMediaList(list: List<ClickableUri>) {
        currentMediaList.value = list
    }

    override val currentSelectedPosition = MutableStateFlow(0)
    override suspend fun saveSelectedPosition(position: Int) {
        currentSelectedPosition.value = position
    }


}