package com.jvrcoding.notemark.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jvrcoding.notemark.auth.presentation.landing.LandingScreenRoot
import com.jvrcoding.notemark.auth.presentation.login.LoginScreenRoot
import com.jvrcoding.notemark.auth.presentation.register.RegisterScreenRoot
import com.jvrcoding.notemark.note.presentation.noteeditor.NoteEditorScreenRoot
import com.jvrcoding.notemark.note.presentation.notelist.NoteListScreenRoot
import com.jvrcoding.notemark.settings.presentation.SettingsScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn) HomeGraph else AuthGraph
    ) {
        authGraph(
            navController = navController
        )
        homeGraph(
            navController = navController
        )
    }

}


private fun NavGraphBuilder.authGraph(
    navController: NavHostController
) {
    navigation<AuthGraph>(
        startDestination = Landing
    ) {
        composable<Landing> {
            LandingScreenRoot(
                onGetStartedClick = {
                    navController.navigate(Register) {
                        popUpTo(Landing) {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    navController.navigate(Login) {
                        popUpTo(Landing) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Login> {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(HomeGraph) {
                        popUpTo(AuthGraph) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Register) {
                        popUpTo(Login) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
        composable<Register> {
            RegisterScreenRoot(
                onSuccessfulRegistration = {
                    navController.navigate(Login) {
                        popUpTo(Register) {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    navController.navigate(Login) {
                        popUpTo(Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.homeGraph(
    navController: NavHostController
) {
    navigation<HomeGraph>(
        startDestination = NoteList
    ) {
        composable<NoteList> {
            NoteListScreenRoot(
                onSettingsClick = {
                    navController.navigate(Settings)
                },
                onTapNote = { noteId ->
                    navController.navigate(
                        NoteEditor(id = noteId)
                    )
                },
                onSuccessfulAdd = { noteId ->
                    navController.navigate(
                        NoteEditor(
                            id = noteId,
                            isNewNote = true
                        )
                    )
                }
            )
        }

        composable<NoteEditor> {
            NoteEditorScreenRoot(
                onSuccessfulDelete = {
                    navController.navigateUp()
                },
                onSuccessfulSave = {
                    navController.navigateUp()
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable<Settings> {
            SettingsScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                },
                onSuccessfulLogout =  {
                    navController.navigate(Login) {
                        popUpTo(HomeGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
