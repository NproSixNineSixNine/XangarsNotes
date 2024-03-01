
package com.example.xangarsassignment.data.repositories.doodles.impl

import com.example.xangarsassignment.data.local.DoodleDao
import com.example.xangarsassignment.data.repositories.doodles.DoodlesRepository
import com.example.xangarsassignment.models.Doodle

class DoodlesRepositoryImpl(private val doodleDao: DoodleDao) : DoodlesRepository {
    override suspend fun create(doodle: Doodle) = doodleDao.insert(doodle)
    override suspend fun getDoodleById(doodleId: String) = doodleDao.getDoodleById(doodleId)
    override suspend fun deleteDoodleById(doodleId: String) = doodleDao.deleteDoodle(doodleId)
    override suspend fun deleteDoodleByNoteId(noteId: String) = doodleDao.deleteDoodleByNote(noteId)
}