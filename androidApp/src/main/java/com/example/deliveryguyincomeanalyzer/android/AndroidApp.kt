package com.example.deliveryguyincomeanalyzer.android

import android.app.Application
import com.example.deliveryguyincomeanalyzer.android.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class AndroidApp:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@AndroidApp)
            androidLogger()

            modules(sharedModule)
        }

    }

}