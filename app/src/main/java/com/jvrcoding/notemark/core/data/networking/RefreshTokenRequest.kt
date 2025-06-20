package com.jvrcoding.notemark.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)
