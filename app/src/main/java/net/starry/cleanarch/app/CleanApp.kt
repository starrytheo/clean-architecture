package net.starry.cleanarch.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CleanApp : Application() {
    override fun onCreate() {
        super.onCreate()


    }
}