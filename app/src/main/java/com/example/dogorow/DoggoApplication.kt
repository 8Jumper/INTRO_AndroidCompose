package com.example.dogorow

import android.app.Application
import com.example.dogorow.data.AppContainer
import com.example.dogorow.data.DefaultAppContainer

class DoggoApplication: Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}