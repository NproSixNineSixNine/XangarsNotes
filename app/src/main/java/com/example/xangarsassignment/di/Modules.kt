package com.example.xangarsassignment.di


import com.example.xangarsassignment.ui.screens.home.HomeViewModel

import com.example.xangarsassignment.ui.screens.note.note.NoteViewModel
import com.example.xangarsassignment.ui.screens.note.preview.PreviewViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * modules for dependency injection where [single] represents singleton class
 */
var databaseModule = module {
    single { getDb(androidApplication()) }
    single { getNoteTableDao(get()) }
    single { getDoodleTableDao(get()) }
    single { getImageTableDao(get()) }
    single { getFolderTableDao(get()) }
}

var viewModel = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { NoteViewModel(get(), get()) }
    viewModel { PreviewViewModel(get(), get()) }
}
var repositories = module {
    single { getLocalRepository() }
    single { getDoodleRepository(get()) }
    single { getImageRepository(get()) }
    single { getNotesRepository(get(), get(), get()) }
}
var utils = module {
    factory { getRandomNumber() }
}
