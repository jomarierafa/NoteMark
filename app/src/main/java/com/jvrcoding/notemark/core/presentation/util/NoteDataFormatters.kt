package com.jvrcoding.notemark.core.presentation.util

fun String.previewWithEllipsis(maxChars: Int): String {
    return if (this.length > maxChars) {
        this.take(maxChars).trimEnd() + "…"
    } else {
        this
    }
}