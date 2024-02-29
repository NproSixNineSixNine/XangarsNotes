package com.example.xangarsassignment

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //Inject Android context here
            androidContext(applicationContext)
            TODO(
                // Add all modules when created
            )
        }
    }
}