package com.example.geminiai.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Chat::class], version = 1)
abstract class DataBase:RoomDatabase() {
    abstract fun chatdao():Dao
}