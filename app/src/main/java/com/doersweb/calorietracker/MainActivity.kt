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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doersweb.calorietracker.navigation.navigate
import com.doersweb.calorietracker.ui.theme.CalorieTrackerTheme
import com.doersweb.core.domain.preferences.Preferences
import com.doersweb.core.navigation.Routes
import com.doersweb.onboarding_presentation.WelcomeScreen
import com.doersweb.onboarding_presentation.activity.ActivityLevelScreen
import com.doersweb.onboarding_presentation.age.AgeScreen
import com.doersweb.onboarding_presentation.gender.GenderScreen
import com.doersweb.onboarding_presentation.goal.GoalScreen
import com.doersweb.onboarding_presentation.height.HeightScreen
import com.doersweb.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.doersweb.onboarding_presentation.weight.WeightScreen
import com.doersweb.tracker_presentation.search.SearchScreen
import com.doersweb.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
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
                        startDestination = if(shouldShowOnboarding) Routes.WELCOME else Routes.TRACKER_OVERVIEW
                    ) {
                        composable(Routes.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.AGE) {
                            AgeScreen(
                                onNavigate = navController::navigate,
                                snackBarState = snackBarState
                            )
                        }
                        composable(Routes.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.HEIGHT) {
                            HeightScreen(
                                onNavigate = navController::navigate,
                                snackBarState = snackBarState
                            )
                        }
                        composable(Routes.WEIGHT) {
                            WeightScreen(
                                onNavigate = navController::navigate,
                                snackBarState = snackBarState
                            )
                        }
                        composable(Routes.GOAL) {
                            GoalScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.ACTIVITY) {
                            ActivityLevelScreen(onNavigate = navController::navigate)
                        }
                        composable(Routes.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                onNavigate = navController::navigate,
                                snackBarState = snackBarState
                            )
                        }
                        composable(Routes.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigate = navController::navigate)
                        }
                        composable(
                            route = Routes.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },
                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                snackbarHostState = snackBarState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}