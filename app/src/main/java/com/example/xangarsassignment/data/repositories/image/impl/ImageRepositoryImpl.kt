
package com.example.xangarsassignment.data.repositories.image.impl

import com.example.xangarsassignment.data.local.ImageDao
import com.example.xangarsassignment.data.repositories.image.ImageRepository
import com.example.xangarsassignment.models.Image


class ImageRepositoryImpl(private val imageDao: ImageDao) : ImageRepository {
    override suspend fun create(image: Image) {
        imageDao.insert(image)
    }

    override suspend fun getImageById(imageId: String): Image? {
        return imageDao.getImageById(imageId)
    }

    override suspend fun deleteImageById(imageId: String) {
        imageDao.deleteImage(imageId)
    }

    override suspend fun deleteImageByNoteId(noteId: String) {
        imageDao.deleteImageByNote(noteId)
    }
}