package com.glucode.eskomseapiclient.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaInformationResponse(
    @SerialName("events")
    val events: List<Event>,
    @SerialName("info")
    val info: Info,
    @SerialName("schedule")
    val schedule: Schedule
) {
    @Serializable
    data class Event(
        @SerialName("end")
        val end: String,
        @SerialName("note")
        val note: String,
        @SerialName("start")
        val start: String
    )

    @Serializable
    data class Info(
        @SerialName("name")
        val name: String,
        @SerialName("region")
        val region: String
    )

    @Serializable
    data class Schedule(
        @SerialName("days")
        val days: List<Day>,
        @SerialName("source")
        val source: String
    ) {
        @Serializable
        data class Day(
            @SerialName("date")
            val date: String,
            @SerialName("name")
            val name: String,
            @SerialName("stages")
            val stages: List<List<String>>
        )
    }
}