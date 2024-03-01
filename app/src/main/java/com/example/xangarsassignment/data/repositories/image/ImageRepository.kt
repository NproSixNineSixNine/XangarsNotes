package com.example.xangarsassignment.data.repositories.image

import com.example.xangarsassignment.models.Image

interface ImageRepository {
    suspend fun create(image: Image)
    suspend fun getImageById(imageId: String): Image?
    suspend fun deleteImageById(imageId: String)
    suspend fun deleteImageByNoteId(noteId: String)
}