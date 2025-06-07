package com.jvrcoding.notemark.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val expirationTimestamp: Long
)