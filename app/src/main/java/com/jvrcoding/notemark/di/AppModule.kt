package com.jvrcoding.notemark.di

import com.jvrcoding.notemark.NoteMarkApp
import com.jvrcoding.notemark.auth.data.EmailPatternValidator
import com.jvrcoding.notemark.auth.domain.PatternValidator
import com.jvrcoding.notemark.auth.domain.UserDataValidator
import com.jvrcoding.notemark.auth.presentation.register.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as NoteMarkApp).applicationScope
    }

    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)


    viewModelOf(::RegisterViewModel)
}