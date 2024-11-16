/***********************
 * Assignment 5
 * Zhong Zheng
 * Oregon State University
 * CS 492
 */
package com.example.mobiletreasurehunt.ui

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.mobiletreasurehunt.data.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.widget.Chronometer

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun displayHint(req: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(needHint = req)
        }
    }

    fun displayHint2(req: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(needHint2 = req)
        }
    }

    fun displayWrongLoc(req: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(wrongLoc = req)
        }
    }

    fun resetHunt() {
        _uiState.value = GameUiState(
            needHint = false,
            distanceToClue1 = 9999999.99,
            location = Location("dummyprovider")
        )
    }
}