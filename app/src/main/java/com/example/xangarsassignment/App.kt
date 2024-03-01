package com.example.xangarsassignment

import android.app.Application
import com.example.xangarsassignment.di.databaseModule
import com.example.xangarsassignment.di.repositories
import com.example.xangarsassignment.di.utils
import com.example.xangarsassignment.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //Inject Android context here
            androidContext(applicationContext)
            koin.loadModules(listOf(databaseModule, viewModel, repositories, utils))
        }
    }
}