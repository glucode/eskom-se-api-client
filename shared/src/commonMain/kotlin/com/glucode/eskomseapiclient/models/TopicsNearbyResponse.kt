package com.glucode.eskomseapiclient.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicsNearbyResponse(
    @SerialName("topics")
    val topics: List<Topic>
) {
    @Serializable
    data class Topic(
        @SerialName("active")
        val active: String,
        @SerialName("body")
        val body: String,
        @SerialName("category")
        val category: String,
        @SerialName("distance")
        val distance: Double,
        @SerialName("followers")
        val followers: Int,
        @SerialName("timestamp")
        val timestamp: String
    )
}