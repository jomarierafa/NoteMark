package com.jvrcoding.notemark.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jvrcoding.notemark.auth.presentation.landing.LandingScreenRoot
import com.jvrcoding.notemark.auth.presentation.login.LoginScreenRoot
import com.jvrcoding.notemark.auth.presentation.register.RegisterScreenRoot
import com.jvrcoding.notemark.dashboard.presentation.DashboardScreen

@Composable
fun NavigationRoot(
    modifier: Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isLoggedIn) HomeGraph else AuthGraph
    ) {
        authGraph(
            modifier = modifier,
            navController = navController
        )
        homeGraph(
            modifier = modifier,
            navController = navController
        )
    }

}


private fun NavGraphBuilder.authGraph(
    modifier: Modifier,
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
                modifier = modifier,
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
                modifier = modifier,
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
    modifier: Modifier,
    navController: NavHostController
) {
    navigation<HomeGraph>(
        startDestination = Dashboard
    ) {
        composable<Dashboard> {
            DashboardScreen()
        }
    }
}

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
        composable<Dashboard> {
            DashboardScreen()
        }
    }
}