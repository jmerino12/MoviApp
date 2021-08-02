package com.jmb.moviapp

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke


class App : Application() {

    suspend fun isConnected(): Boolean = (Dispatchers.IO) {
        val command = "ping -c 1 google.com"
        Runtime.getRuntime().exec(command).waitFor() == 0
    }
}