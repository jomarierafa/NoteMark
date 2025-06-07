package com.jvrcoding.notemark.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}