package com.example.clashofbattle

import android.app.Application
import com.example.clashofbattle.database.PlayerDatabase

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PlayerDatabase.init(this)
    }
}