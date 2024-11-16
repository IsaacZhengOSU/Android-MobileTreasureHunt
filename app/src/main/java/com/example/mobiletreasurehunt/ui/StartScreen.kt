/***********************
 * Assignment 5
 * Zhong Zheng
 * Oregon State University
 * CS 492
 */
package com.example.mobiletreasurehunt.ui

import android.widget.Chronometer
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.model.Rules
import com.example.mobiletreasurehunt.ui.theme.MobileTreasureHuntTheme

@Composable
fun StartScreen(
    ruleList: List<Rules>,
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(top = 60.dp),
            //.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Text("Rules",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Box(
            modifier = Modifier
                .height(500.dp)
                //.verticalScroll(rememberScrollState())
        ) {
            LazyColumn(modifier = Modifier
                .padding(bottom = 50.dp)
                .wrapContentHeight(),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(ruleList) { rule ->
                    RuleCard(
                        rule = rule,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }}

        Button(
            onClick = {onNextButtonClicked()},
            //modifier = modifier.widthIn(min = 250.dp)
        ) {
            Text("Start",
                color = Color.White
            )
        }
    }
}

@Composable
fun RuleCard(rule: Rules, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(
            text = LocalContext.current.getString(rule.stringResourceId1),
            fontSize = 36.sp,
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    MobileTreasureHuntTheme {
        StartScreen(
//            chronometer = null,
            ruleList = DataSource(0).loadRules(),
            onNextButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray),
        )
    }
}