package com.example.fetchtakehome

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule

abstract class HiringTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    protected fun getListIdString(id: Int) = composeTestRule.activity.getString(R.string.list_header, id)
}