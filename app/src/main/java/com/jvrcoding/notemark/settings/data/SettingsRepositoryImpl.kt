package com.jvrcoding.notemark.settings.data

import com.jvrcoding.notemark.core.data.networking.post
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.EmptyResult
import com.jvrcoding.notemark.settings.domain.SettingsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider

class SettingsRepositoryImpl(
    private val client: HttpClient,
    private val sessionStorage: SessionStorage,
): SettingsRepository {

    override suspend fun logout(): EmptyResult<DataError.Network> {
        val result = client.post<LogoutRequest, Unit>(
            route = "/api/auth/logout",
            body =  LogoutRequest(
                refreshToken = sessionStorage.get()?.refreshToken ?: "",
            )
        )

        client.authProvider<BearerAuthProvider>()?.clearToken()

        return result
    }
}