package com.glucode.eskomseapiclient.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    @SerialName("status")
    val status: Status
) {
    @Serializable
    data class Status(
        @SerialName("capetown")
        val capetown: Summary,
        @SerialName("eskom")
        val eskom: Summary
    ) {
        @Serializable
        data class Summary(
            @SerialName("name")
            val name: String,
            @SerialName("next_stages")
            val nextStages: List<NextStage>,
            @SerialName("stage")
            val stage: String,
            @SerialName("stage_updated")
            val stageUpdated: String
        ) {
            @Serializable
            data class NextStage(
                @SerialName("stage")
                val stage: String,
                @SerialName("stage_start_timestamp")
                val stageStartTimestamp: String
            )
        }
    }
}