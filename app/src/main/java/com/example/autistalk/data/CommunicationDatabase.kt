package com.example.autistalk.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

abstract class AppDatabase : RoomDatabase(){
    abstract val cardDao: CardDao

    companion object {
        const val NAME = "communication_card_app_db"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, NAME)
                        .build()
                }
            }
            return instance!!
        }
    }
}