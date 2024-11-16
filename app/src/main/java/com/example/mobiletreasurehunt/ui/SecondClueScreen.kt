/***********************
 * Assignment 5
 * Zhong Zheng
 * Oregon State University
 * CS 492
 */
package com.example.mobiletreasurehunt.ui

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mobiletreasurehunt.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.mobiletreasurehunt.ui.component.formatTime
import com.example.mobiletreasurehunt.ui.theme.MobileTreasureHuntTheme
import com.example.whereisbenny.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@Composable
fun SecondClueScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    onHintButtonClicked: (Boolean) -> Unit = {},
    correctLocationFound: () -> Unit = {},
    wrongLocationFound: (Boolean) -> Unit = {},
    onQuitButtonClicked: () -> Unit = {},
    context: Context,
    timerValue: Long
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val location = remember { mutableStateOf<Location?>(null) }
    val distance = remember { mutableStateOf<Double?>(null) }
    val permissionGranted = remember { mutableStateOf(false) }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted.value = isGranted
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                permissionGranted.value = true
            }
            else -> {
                locationPermissionRequest.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Text(text = stringResource(R.string.Textual_Clue2),
            color = Color.White)

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Button(
            shape = RoundedCornerShape(15),
            onClick = { onHintButtonClicked(true) }
        ) {
            Text(text = "hint")
        }

        Text(text = timerValue.formatTime(), fontSize = 24.sp, color = Color.White)

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        Button(onClick = {
            if (permissionGranted.value) {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).addOnSuccessListener { loc: Location? ->
                    // Home of the Beavs! Yah Pac2!
                    val clueTargetLat = 44.56535251331007
                    val clueTargetLon = -123.27600947886927
                    if (loc != null) {
                        // Safely calculate the distance to the target location (In Kilometers)
                        val calculatedDistance = LocationUtils.calculateDistance(
                            clueTargetLat,
                            clueTargetLon,
                            loc.latitude,
                            loc.longitude
                        )
                        Log.e("loc2", "loc2: " + loc.toString())
                        distance.value = calculatedDistance
                        Log.e("dist2", "dist2: " + distance.value.toString())
                        if (distance.value!! < 5)
                            correctLocationFound()
                        else wrongLocationFound(true)
                    }
                    // Update location
                    //location.value = loc
                }
            }
        }) {
            Text(text = "Found it!")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            shape = RoundedCornerShape(15),
            onClick = { onQuitButtonClicked() }
        ) {
            Text(text = "Quit")
        }

        if (gameUiState.needHint2)
            HintDialog(
                modifier = modifier,
                onHintButtonClicked = onHintButtonClicked
            )
        if (gameUiState.wrongLoc)
            FoundDialog(
                modifier = modifier,
                wrongLocationFound = wrongLocationFound
            )
    }
}

@Composable
private fun HintDialog(
    modifier: Modifier = Modifier,
    onHintButtonClicked: (Boolean) -> Unit = {},
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { onHintButtonClicked(false) },
            title = { Text(text = stringResource(R.string.hint2)) },
            modifier = modifier.fillMaxSize(),
            dismissButton = {
                TextButton(
                    onClick = { onHintButtonClicked(false) }
                ) {
                    Text(text = "continue")
                }
            },
            confirmButton = {}
        )
    }
}

@Composable
private fun FoundDialog(
    modifier: Modifier = Modifier,
    wrongLocationFound: (Boolean) -> Unit = {},
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { wrongLocationFound(false) },
            title = { Text(text = stringResource(R.string.wrong_location)) },
            modifier = modifier.fillMaxSize(),
            dismissButton = {
                TextButton(
                    onClick = { wrongLocationFound(false) }
                ) {
                    Text(text = "continue")
                }
            },
            confirmButton = {}
        )
    }
}

@Preview
@Composable
fun SecondClueScreenPrev() {
    MobileTreasureHuntTheme {
        SecondClueScreen(
            modifier = Modifier.fillMaxHeight(),
            context =  LocalContext.current,
            timerValue = 0
        )
    }
}
