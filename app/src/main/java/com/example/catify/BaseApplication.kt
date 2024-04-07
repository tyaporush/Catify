package com.example.catify

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        val scope = CoroutineScope(Dispatchers.IO)
    }
}