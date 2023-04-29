package com.doersweb.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doersweb.calorietracker.navigation.navigate
import com.doersweb.calorietracker.ui.theme.CalorieTrackerTheme
import com.doersweb.core.navigation.Routes
import com.doersweb.onboarding_presentation.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.WELCOME
                    ) {
                        composable(Routes.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.AGE) {

                        }
                        composable(Routes.GENDER) {

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