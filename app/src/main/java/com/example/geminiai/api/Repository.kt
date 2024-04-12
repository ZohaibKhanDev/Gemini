package com.example.geminiai.api

import androidx.room.Database
import com.example.geminiai.database.Chat
import com.example.geminiai.database.DataBase

class Repository(private val database: DataBase) :GeminiApi {
    override suspend fun getAllGemini(content: String): Gemini {
        return GeminiApiClient.getAllGemini(content)
    }

    fun getAllChat():List<Chat>{
        return database.chatdao().getAllChat()
    }

    fun Insert(chat: Chat){
        return database.chatdao().Inset(chat)
    }
}