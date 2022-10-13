package com.glucode.eskomseapiclient.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllowanceResponse(
    @SerialName("allowance")
    val allowance: Allowance
) {
    @Serializable
    data class Allowance(
        @SerialName("count")
        val count: Int,
        @SerialName("limit")
        val limit: Int,
        @SerialName("type")
        val type: String
    )
}