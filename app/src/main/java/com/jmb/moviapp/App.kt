package com.jmb.moviapp

import android.app.Application
import okio.IOException


class App : Application() {

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }
}