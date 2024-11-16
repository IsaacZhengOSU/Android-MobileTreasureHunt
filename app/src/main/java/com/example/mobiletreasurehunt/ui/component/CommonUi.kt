package com.example.mobiletreasurehunt.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color


@Composable
fun FormattedText(@StringRes str: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(str),
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge,
        color = Color.White
    )
}