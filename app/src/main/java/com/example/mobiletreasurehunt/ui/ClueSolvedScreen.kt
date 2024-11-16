/***********************
 * Assignment 5
 * Zhong Zheng
 * Oregon State University
 * CS 492
 */
package com.example.mobiletreasurehunt.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mobiletreasurehunt.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mobiletreasurehunt.ui.component.formatTime
import com.example.mobiletreasurehunt.ui.theme.MobileTreasureHuntTheme

@Composable
fun ClueSolvedScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    timerViewModel: TimerViewModel,
    onHintButtonClicked: (Boolean) -> Unit = {},
    onContinueButtonClicked: () -> Unit = {},
    timerValue: Long
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Text(
            text = "First Clue Solved!",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Text(text = timerValue.formatTime(), fontSize = 24.sp, color = Color.White)

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Text(
            text = stringResource(R.string.more_info),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Button(
            shape = RoundedCornerShape(15),
            onClick = { onContinueButtonClicked() }
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview
@Composable
fun ClueSolvedScreenScreenPreview() {
    MobileTreasureHuntTheme {
        ClueSolvedScreen(
            modifier = Modifier.fillMaxHeight(),
            timerViewModel = TimerViewModel(),
            timerValue = 100
        )
    }
}
