package com.example.geminiai.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface Dao {
    @Query("SELECT * FROM chat")
    fun getAllChat():List<Chat>
    @Insert
    fun Inset(chat: Chat)
}