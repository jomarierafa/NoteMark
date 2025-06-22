package com.jvrcoding.notemark.core.presentation.util

fun String.getInitials(): String {
    val parts = this.trim().split("\\s+".toRegex())

    return when (parts.size) {
        0 -> ""
        1 -> parts[0].take(2).uppercase()
        else -> "${parts.first().first()}${parts.last().first()}".uppercase()
    }
}