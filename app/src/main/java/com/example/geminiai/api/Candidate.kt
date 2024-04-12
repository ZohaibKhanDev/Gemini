package com.example.geminiai.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Candidate(
    @SerialName("content")
    val content: Content,
    @SerialName("finishReason")
    val finishReason: String,
    @SerialName("index")
    val index: Int,
    @SerialName("safetyRatings")
    val safetyRatings: List<SafetyRating>?=null
)