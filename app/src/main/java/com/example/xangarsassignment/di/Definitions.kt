package com.example.xangarsassignment.di

import android.content.Context
import androidx.room.Room
import com.example.xangarsassignment.data.local.*
import com.example.xangarsassignment.data.repositories.doodles.DoodlesRepository
import com.example.xangarsassignment.data.repositories.doodles.impl.DoodlesRepositoryImpl
import com.example.xangarsassignment.data.repositories.image.ImageRepository
import com.example.xangarsassignment.data.repositories.image.impl.ImageRepositoryImpl
import com.example.xangarsassignment.data.repositories.local.LocalRepository
import com.example.xangarsassignment.data.repositories.local.impl.LocalRepositoryImpl
import com.example.xangarsassignment.data.repositories.notes.NotesRepository
import com.example.xangarsassignment.data.repositories.notes.impl.NotesRepositoryImpl
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * definitions for dependency injection for all selected classes
 */

fun getDb(context: Context): AppDatabase {
    return synchronized(context) {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-xangars"
        ).fallbackToDestructiveMigration().addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
    }
}

fun getNoteTableDao(appDatabase: AppDatabase) = appDatabase.noteDao()
fun getDoodleTableDao(appDatabase: AppDatabase) = appDatabase.doodleDao()
fun getImageTableDao(appDatabase: AppDatabase) = appDatabase.imageDao()

fun getFolderTableDao(appDatabase: AppDatabase) = appDatabase.folderDao()

fun getNotesRepository(
    notesDao: NoteDao,
    doodleDao: DoodleDao,
    imageDao: ImageDao
): NotesRepository = NotesRepositoryImpl(notesDao, doodleDao, imageDao)

fun getDoodleRepository(doodleDao: DoodleDao): DoodlesRepository = DoodlesRepositoryImpl(doodleDao)
fun getImageRepository(imageDao: ImageDao): ImageRepository = ImageRepositoryImpl(imageDao)

fun getLocalRepository(): LocalRepository = LocalRepositoryImpl()


fun getRandomNumber() = Random(System.currentTimeMillis()).nextInt(0..17)
