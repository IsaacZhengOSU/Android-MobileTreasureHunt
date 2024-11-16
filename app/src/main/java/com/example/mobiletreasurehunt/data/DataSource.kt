/***********************
 * Assignment 4
 * Zhong Zheng
 * Oregon State University
 * CS 492
 */
package com.example.mobiletreasurehunt.data

import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.model.Rules
import java.sql.Time

data class DataSource (

    var timeConsume: Int
) {
    fun loadRules(): List<Rules> {
        return listOf<Rules>(
            Rules(R.string.rule1),
            Rules(R.string.rule2),
            Rules(R.string.rule3)
        )
    }
}