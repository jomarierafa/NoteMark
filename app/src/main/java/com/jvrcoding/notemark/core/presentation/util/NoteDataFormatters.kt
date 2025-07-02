package com.jvrcoding.notemark.core.presentation.util

import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.previewWithEllipsis(maxChars: Int): String {
    return if (this.length > maxChars) {
        this.take(maxChars).trimEnd() + "…"
    } else {
        this
    }
}

fun ZonedDateTime.formatWithJustNow(
    pattern: String = "dd MMM yyyy, HH:mm",
    locale: Locale = Locale.getDefault()
): String {
    val now = ZonedDateTime.now()
    val duration = Duration.between(this, now).abs()

    return if (duration.toMinutes() < 5) {
        "Just now"
    } else {
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)
        this.format(formatter)
    }
}