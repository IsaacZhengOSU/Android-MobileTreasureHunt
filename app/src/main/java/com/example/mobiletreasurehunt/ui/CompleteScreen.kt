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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.ui.component.formatTime
import com.example.mobiletreasurehunt.ui.theme.MobileTreasureHuntTheme

@Composable
fun CompleteScreen(
    modifier: Modifier = Modifier,
    onHomeButtonClicked: () -> Unit = {},
    timerValue: Long
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Text(
            text = "Congratulations!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = timerValue.formatTime(), fontSize = 24.sp, color = Color.White)

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.Final_Clue_Info),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            shape = RoundedCornerShape(15),
            onClick = { onHomeButtonClicked() }
        ) {
            Text(text = "Home")
        }

    }
}

@Preview
@Composable
fun CompleteScreenPreview() {
    MobileTreasureHuntTheme {
        CompleteScreen(
            modifier = Modifier.fillMaxHeight(),
            onHomeButtonClicked = {},
            timerValue = 0
        )
    }
}