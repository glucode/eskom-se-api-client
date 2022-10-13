package com.glucode.eskomseapiclient.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreasNearbyResponse(
    @SerialName("areas")
    val areas: List<Area>
) {
    @Serializable
    data class Area(
        @SerialName("count")
        val count: Int,
        @SerialName("id")
        val id: String
    )
}