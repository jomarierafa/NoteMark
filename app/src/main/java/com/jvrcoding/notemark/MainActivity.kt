package com.jvrcoding.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.jvrcoding.notemark.core.navigation.NavigationRoot
import com.jvrcoding.notemark.ui.theme.NoteMarkTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }
        enableEdgeToEdge()
        setContent {
            NoteMarkTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)) { innerPadding ->
                    if(!viewModel.state.isCheckingAuth) {
                        val navController = rememberNavController()
                        NavigationRoot(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(top = innerPadding.calculateTopPadding()),
                            navController = navController,
                            isLoggedIn = viewModel.state.isLoggedIn
                        )
                    }
                }
            }
        }
    }
}
