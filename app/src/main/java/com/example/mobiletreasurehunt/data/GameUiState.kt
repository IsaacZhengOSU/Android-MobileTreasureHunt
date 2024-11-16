/***********************
 * Assignment 5
 * Zhong Zheng
 * Oregon State University
 * CS 492
 */
package com.example.mobiletreasurehunt.data

import android.location.Location
import android.widget.Chronometer

data class GameUiState(
    var needHint: Boolean = false,
    var needHint2: Boolean = false,
    var wrongLoc: Boolean = false,
    var distanceToClue1: Double? = 0.00,
    var location: Location? = null,
    var distanceToClue2: Double? = 0.00,
    var location2: Location? = null,
    var timePast: Chronometer? = null,

    )