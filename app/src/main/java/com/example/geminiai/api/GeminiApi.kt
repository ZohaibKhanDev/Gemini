package com.example.geminiai.api

interface GeminiApi {
    suspend fun getAllGemini(content:String):Gemini

}