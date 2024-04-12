package com.example.geminiai.api

import com.example.geminiai.api.Constant.APIKEY
import com.example.geminiai.api.Constant.TIMEOUT
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object GeminiApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    private val Client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }

            }
        }

        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
        }

    }

    @OptIn(InternalAPI::class)
    suspend fun getAllGemini(content: String): Gemini {
        val url =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$APIKEY"

        val requestBody = mapOf(
            "contents" to listOf(
                mapOf("parts" to listOf(mapOf("text" to content)))
            )
        )

        try {
            val responseText: String = Client.post(url) {
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                body = Json.encodeToString(requestBody)
            }.bodyAsText()

            println("API Response: $responseText")

            return Json.decodeFromString(responseText)
        } catch (e: Exception) {
            // Handle any exceptions that might occur during the network request or JSON parsing
            println("Error during API request: ${e.message}")
            throw e // Rethrow the exception to signal that an error occurred
        }
    }
}
