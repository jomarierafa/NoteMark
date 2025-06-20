package com.jvrcoding.notemark.core.data.networking

import com.jvrcoding.notemark.core.domain.AuthInfo
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {

    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("X-User-Email","jomarierafa1717@gmail.com")
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val tokenPair = sessionStorage.get()
                        BearerTokens(
                            accessToken = tokenPair?.accessToken ?: "",
                            refreshToken = tokenPair?.refreshToken ?: ""
                        )
                    }
                    refreshTokens {
                        val tokenPair =  sessionStorage.get()

                        val response = client.post<RefreshTokenRequest, RefreshTokenResponse>(
                            route = "/api/auth/refresh",
                            body = RefreshTokenRequest(
                                refreshToken = tokenPair?.refreshToken ?: "",
                            )
                        )

                        if(response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = response.data.refreshToken,
                                username = tokenPair?.username ?: ""
                            )
                            sessionStorage.set(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }

                    }
                }
            }
        }
    }

}