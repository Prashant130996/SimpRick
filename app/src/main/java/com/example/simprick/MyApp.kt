package com.example.simprick

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

private var sharedApplicationContextBackingProperty: Context? = null

val sharedApplicationContext
    get() = sharedApplicationContextBackingProperty
        ?: throw IllegalStateException("Application context not initialised yet")

@HiltAndroidApp
class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        sharedApplicationContextBackingProperty = applicationContext
    }
}