package com.example.geminiai.api

class Repository:GeminiApi {
    override suspend fun getAllGemini(content: String): Gemini {
        return GeminiApiClient.getAllGemini(content)
    }
}