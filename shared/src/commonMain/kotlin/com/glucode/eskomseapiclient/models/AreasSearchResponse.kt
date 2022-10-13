package com.glucode.eskomseapiclient.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreasSearchResponse(
    @SerialName("areas")
    val areas: List<Area>
) {
    @Serializable
    data class Area(
        @SerialName("id")
        val id: String,
        @SerialName("name")
        val name: String,
        @SerialName("region")
        val region: String
    )
}