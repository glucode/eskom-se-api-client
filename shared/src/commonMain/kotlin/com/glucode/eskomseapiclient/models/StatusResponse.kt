package com.glucode.eskomseapiclient.models

data class StatusResponse(
    val status: Status
)

data class Status(
    val capetown: Summary,
    val eskom: Summary
)

data class Summary(
    val name: String,
    val next_stages: List<NextStage>,
    val stage: String,
    val stage_updated: String
)

data class NextStage(
    val stage: String,
    val stage_start_timestamp: String
)