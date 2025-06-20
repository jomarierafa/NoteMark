package com.jvrcoding.notemark.di

import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.jvrcoding.notemark.MainViewModel
import com.jvrcoding.notemark.NoteMarkApp
import com.jvrcoding.notemark.auth.data.AuthRepositoryImpl
import com.jvrcoding.notemark.auth.data.EmailPatternValidator
import com.jvrcoding.notemark.auth.domain.AuthRepository
import com.jvrcoding.notemark.auth.domain.PatternValidator
import com.jvrcoding.notemark.auth.domain.UserDataValidator
import com.jvrcoding.notemark.auth.presentation.login.LoginViewModel
import com.jvrcoding.notemark.auth.presentation.register.RegisterViewModel
import com.jvrcoding.notemark.core.data.auth.EncryptedSessionStorage
import com.jvrcoding.notemark.core.data.database.NoteDatabase
import com.jvrcoding.notemark.core.data.database.RoomLocalNoteDataSource
import com.jvrcoding.notemark.core.data.networking.HttpClientFactory
import com.jvrcoding.notemark.core.data.note.NoteRepositoryImpl
import com.jvrcoding.notemark.core.domain.SessionStorage
import com.jvrcoding.notemark.core.domain.note.LocalNoteDataSource
import com.jvrcoding.notemark.core.domain.note.NoteRepository
import com.jvrcoding.notemark.core.domain.note.RemoteNoteDataSource
import com.jvrcoding.notemark.note.data.KtorRemoteNoteDataSource
import com.jvrcoding.notemark.note.presentation.noteeditor.NoteEditorViewModel
import com.jvrcoding.notemark.note.presentation.notelist.NoteListViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //app
    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(),
            "auth_pref",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    single<CoroutineScope> {
        (androidApplication() as NoteMarkApp).applicationScope
    }

   //data
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::NoteRepositoryImpl).bind<NoteRepository>()

    //database
    single {
        Room.databaseBuilder(
            androidApplication(),
            NoteDatabase::class.java,
            "note.db"
        ).build()
    }
    single { get<NoteDatabase>().noteDao }
    singleOf(::RoomLocalNoteDataSource).bind<LocalNoteDataSource>()

    //network
    singleOf(::KtorRemoteNoteDataSource).bind<RemoteNoteDataSource>()



    //viewmodel
    viewModelOf(::MainViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::NoteListViewModel)
    viewModelOf(::NoteEditorViewModel)
}