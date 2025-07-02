package com.jvrcoding.notemark.settings.domain

import com.jvrcoding.notemark.core.domain.util.DataError
import com.jvrcoding.notemark.core.domain.util.EmptyResult

interface SettingsRepository {
    suspend fun logout(): EmptyResult<DataError.Network>
}