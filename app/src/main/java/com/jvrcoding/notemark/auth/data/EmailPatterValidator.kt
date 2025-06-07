package com.jvrcoding.notemark.auth.data

import android.util.Patterns
import com.jvrcoding.notemark.auth.domain.PatternValidator

object EmailPatternValidator: PatternValidator {

    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}