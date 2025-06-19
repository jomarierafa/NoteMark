package com.jvrcoding.notemark.auth.data

import com.jvrcoding.notemark.auth.domain.AuthRepository
import com.jvrcoding.notemark.core.data.networking.post
import com.jvrcoding.notemark.core.domain.AuthInfo
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.EmptyResult
import com.jvrcoding.notemark.core.domain.util.Result
import com.jvrcoding.notemark.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): AuthRepository {

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/api/auth/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if(result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    username = result.data.username
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/api/auth/register",
            body = RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )
    }


}