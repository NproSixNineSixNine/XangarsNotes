package com.example.xangarsassignment.data.repositories.local

import com.example.xangarsassignment.models.ClickableUri
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

interface LocalRepository {
    suspend fun saveCurrentNote(currentNote: String = UUID.randomUUID().toString())
    val currentNote: MutableStateFlow<String>

    suspend fun saveCurrentDoodleId(currentDoodleId: String)
    val currentDoodleId: MutableStateFlow<String>

    suspend fun saveCurrentImageId(currentImageId: String)
    val currentImageId: MutableStateFlow<String>


    suspend fun saveCurrentMediaList(list: List<ClickableUri>)
    val currentMediaList: MutableStateFlow<List<ClickableUri>>

    suspend fun saveSelectedPosition(position: Int)
    val currentSelectedPosition: MutableStateFlow<Int>
}