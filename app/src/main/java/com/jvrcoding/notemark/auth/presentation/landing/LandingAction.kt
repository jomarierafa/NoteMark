package com.jvrcoding.notemark.auth.presentation.landing

sealed interface LandingAction {
    data object OnGetStartedClick: LandingAction
    data object OnLoginClick: LandingAction
}