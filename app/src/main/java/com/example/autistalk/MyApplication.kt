package com.example.autistalk

import android.app.Application
import androidx.room.Room
import com.example.autistalk.data.AppDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.NAME)
            .build()
    }
}