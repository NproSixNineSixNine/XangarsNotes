package com.example.xangarsassignment.data.local

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.xangarsassignment.models.*
import kotlinx.coroutines.flow.Flow

@Database(
    exportSchema = true,
    version = 5,
    entities = [Note::class, Doodle::class, Image::class, Folder::class],
    autoMigrations = [
        AutoMigration(from = 3, to = 4, spec = AppDatabase.MIGRATION_3_4::class),
        AutoMigration(from = 4, to = 5, spec = AppDatabase.MIGRATION_4_5::class),
    ],
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
    abstract fun doodleDao(): DoodleDao
    abstract fun imageDao(): ImageDao

    @RenameColumn(tableName = "notes_table", fromColumnName = "id", toColumnName = "noteId")
    @DeleteColumn.Entries(
        DeleteColumn(tableName = "notes_table", columnName = "imageText"),
        DeleteColumn(tableName = "notes_table", columnName = "doodle"),
    )
    class MIGRATION_3_4 : AutoMigrationSpec

    class MIGRATION_4_5 : AutoMigrationSpec

}

@Dao
interface DoodleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doodle: Doodle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg doodle: Doodle)

    @Query("SELECT * FROM doodle_table WHERE doodleid = :id")
    suspend fun getDoodleById(id: String): Doodle?

    @Query("DELETE FROM doodle_table WHERE doodleid = :id")
    suspend fun deleteDoodle(id: String)

    @Query("DELETE FROM doodle_table WHERE attachedNoteId = :id")
    suspend fun deleteDoodleByNote(id: String)
}

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Image)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg image: Image)

    @Query("SELECT * FROM image_table WHERE imageId = :id")
    suspend fun getImageById(id: String): Image?

    @Query("DELETE FROM image_table WHERE imageId = :id")
    suspend fun deleteImage(id: String)

    @Query("DELETE FROM image_table WHERE attachedNoteId = :id")
    suspend fun deleteImageByNote(id: String)
}

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    suspend fun getNoteById(id: String): NoteWithDoodleAndImage?

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    fun getNoteByIdByFlow(id: String): Flow<NoteWithDoodleAndImage?>

    @Query("SELECT * FROM notes_table ORDER BY updatedOn DESC")
    fun getAllNotes(): List<Note>

    @Transaction
    @Query("SELECT * FROM notes_table WHERE folderId = :id ORDER BY updatedOn DESC")
    fun getAllNotesByFolderId(id: String): Flow<List<NoteWithDoodleAndImage>>

    @Transaction
    @Query("SELECT * FROM notes_table WHERE description LIKE '%' || :query || '%' ORDER BY updatedOn DESC")
    fun getNotesBySearch(query: String): Flow<List<NoteWithDoodleAndImage>>

    @Query("DELETE FROM notes_table WHERE noteId = :id")
    suspend fun deleteNote(id: String)

    @Query("DELETE FROM notes_table")
    suspend fun deleteTable()

    @Query("SELECT Count(*) FROM notes_table WHERE folderId = :folderId")
    suspend fun getNotesCountByFolderId(folderId: String): Int

}

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: Folder)

    @Query("SELECT * FROM folder_table")
    fun getAllFolders(): List<Folder>

    @Query("DELETE FROM folder_table")
    suspend fun deleteTable()

}