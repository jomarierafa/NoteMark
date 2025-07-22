package com.jvrcoding.notemark.core.presentation.util

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String?.toSyncStatusMessage(): String {
    if (this == null) return "Never synced"

    return try {
        val date = Instant.parse(this).atZone(ZoneId.of("UTC"))
        val now = ZonedDateTime.now()
        val duration = Duration.between(date, now)

        when {
            duration < Duration.ofMinutes(5) -> "Just now"
            duration < Duration.ofHours(1) -> "${duration.toMinutes()} minutes ago"
            duration < Duration.ofDays(1) -> "${duration.toHours()} hours ago"
            duration < Duration.ofDays(7) -> "${duration.toDays()} days ago"
            else -> date.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
        }
    } catch (e: Exception) {
        "Invalid date"
    }
}