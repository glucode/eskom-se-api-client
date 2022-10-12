package com.glucode.eskomseapiclient.models

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val status: Status
)

@Serializable
data class Status(
    val capetown: Summary,
    val eskom: Summary
)

@Serializable
data class Summary(
    val name: String,
    val next_stages: List<NextStage>,
    val stage: String,
    val stage_updated: String
)

@Serializable
data class NextStage(
    val stage: String,
    val stage_start_timestamp: String
)