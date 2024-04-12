package com.example.geminiai.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gemini(
    @SerialName("candidates")
    val candidates: List<Candidate>
)