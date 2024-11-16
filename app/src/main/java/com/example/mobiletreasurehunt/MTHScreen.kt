/***********************
 * Assignment 5
 * Zhong Zheng
 * Oregon State University
 * CS 492
 */
package com.example.mobiletreasurehunt

import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobiletreasurehunt.ui.GameViewModel
import androidx.navigation.compose.NavHost
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.ui.ClueSolvedScreen
import com.example.mobiletreasurehunt.ui.CompleteScreen
import com.example.mobiletreasurehunt.ui.FirstClueScreen
import com.example.mobiletreasurehunt.ui.SecondClueScreen
import com.example.mobiletreasurehunt.ui.StartScreen
import com.example.mobiletreasurehunt.ui.TimerViewModel

enum class MTHScreen {
    Start,
    Clue1,
    Solved,
    Clue2,
    Complete
}

@Composable
fun MTHApp(
    timerViewModel: TimerViewModel,
    gameViewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val timerValue by timerViewModel.timer.collectAsState()
//    val gameUiState by gameViewModel.uiState.collectAsState()
//    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MTHScreen.Start.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.DarkGray)
        ) {
            composable(route = MTHScreen.Start.name) {
                StartScreen(
                    ruleList = DataSource(0).loadRules(),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.DarkGray)
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    onNextButtonClicked = {
                        navController.navigate(MTHScreen.Clue1.name)
                        timerViewModel.startTimer()
                    }
                )
            }
            composable(route = MTHScreen.Clue1.name) {
                val context = LocalContext.current
                FirstClueScreen(
                    gameViewModel = gameViewModel,
                    timerViewModel = timerViewModel,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.DarkGray),
                    onHintButtonClicked = { gameViewModel.displayHint(it) },
                    correctLocationFound = {
                        navController.navigate(MTHScreen.Solved.name )
                        timerViewModel.pauseTimer()
                                           },
                    wrongLocationFound = { gameViewModel.displayWrongLoc(it) },
                    onQuitButtonClicked = { navigateToStart(timerViewModel, gameViewModel, navController) },
                    context = context,
                    timerValue = timerValue
                )
            }
            composable(route = MTHScreen.Solved.name) {
                val context = LocalContext.current
                ClueSolvedScreen(
                    gameViewModel = gameViewModel,
                    timerViewModel = timerViewModel,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.DarkGray),
                    onHintButtonClicked = { gameViewModel.displayHint(it) },
                    onContinueButtonClicked = {
                        navController.navigate(MTHScreen.Clue2.name )
                        timerViewModel.startTimer()
                                              },
                    timerValue = timerValue
                )
            }
            composable(route = MTHScreen.Clue2.name) {
                val context = LocalContext.current
                SecondClueScreen(
                    gameViewModel = gameViewModel,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.DarkGray),
                    onHintButtonClicked = { gameViewModel.displayHint2(it) },
                    correctLocationFound = {
                        navController.navigate(MTHScreen.Complete.name )
                        timerViewModel.pauseTimer()
                                           },
                    wrongLocationFound = { gameViewModel.displayWrongLoc(it) },
                    onQuitButtonClicked = { navigateToStart(timerViewModel,gameViewModel, navController) },
                    context = context,
                    timerValue = timerValue
                )
            }
            composable(route = MTHScreen.Complete.name) {
                val context = LocalContext.current
                CompleteScreen(
                    onHomeButtonClicked = { navigateToStart(timerViewModel, gameViewModel, navController) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.DarkGray),
                    timerValue = timerValue
                )
            }
        }
    }
}

private fun navigateToStart(
    timerViewModel: TimerViewModel,
    viewModel: GameViewModel,
    navController: NavHostController
) {
    viewModel.resetHunt()
    timerViewModel.stopTimer()
    navController.popBackStack(MTHScreen.Start.name, inclusive = false)
}