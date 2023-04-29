package com.doersweb.calorietracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doersweb.calorietracker.navigation.navigate
import com.doersweb.calorietracker.ui.theme.CalorieTrackerTheme
import com.doersweb.core.navigation.Routes
import com.doersweb.onboarding_presentation.WelcomeScreen
import com.doersweb.onboarding_presentation.age.AgeScreen
import com.doersweb.onboarding_presentation.gender.GenderScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val snackBarState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackBarState) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.WELCOME
                    ) {
                        composable(Routes.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.AGE) {
                            AgeScreen(onNavigate = navController::navigate, snackBarState = snackBarState)
                        }
                        composable(Routes.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.HEIGHT) {

                        }
                        composable(Routes.WEIGHT) {

                        }
                        composable(Routes.NUTRIENT_GOAL) {

                        }
                        composable(Routes.ACTIVITY) {

                        }
                        composable(Routes.TRACKER_OVERVIEW) {

                        }
                        composable(Routes.SEARCH) {

                        }
                    }
                }
            }
        }
    }
}