package com.yeminnaing.firebasecomposeproject.presentationLayer

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.analytics
    }
}