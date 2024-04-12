package com.example.geminiai.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    @ColumnInfo("tittle")
    val tittle:String,
)
