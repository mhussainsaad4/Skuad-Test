package com.example.skuadtestapp.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SkuadApplication: Application() {

    override fun onCreate() {
        super.onCreate()

    }
}