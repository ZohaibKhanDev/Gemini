package com.example.geminiai.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    @ColumnInfo("boot")
    val bot:String,
    @ColumnInfo("date")
    val date:String,
)
