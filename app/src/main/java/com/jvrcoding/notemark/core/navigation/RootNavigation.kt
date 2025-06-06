package com.jvrcoding.notemark.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jvrcoding.notemark.auth.presentation.landing.LandingScreenRoot
import com.jvrcoding.notemark.auth.presentation.login.LoginScreenRoot
import com.jvrcoding.notemark.auth.presentation.register.RegisterScreenRoot

@Composable
fun RootNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Landing,
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
                modifier = modifier,
                onLoginSuccess = { },
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
                modifier = modifier,
                onSuccessfulRegistration = {
                    navController.navigate(Login)
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