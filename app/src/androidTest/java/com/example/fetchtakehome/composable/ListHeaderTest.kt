package com.example.fetchtakehome.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class ListHeaderTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun list_header_text_content() {
        composeTestRule.setContent {
            ListHeader(key = 1)
        }
        composeTestRule.onNodeWithText("List ID: 1").assertIsDisplayed()
    }

    @Test
    fun list_header_horizontal_padding() {
        composeTestRule.setContent {
            ListHeader(key = 1)
        }
        composeTestRule.onNodeWithTag("ListHeaderText").assertLeftPositionInRootIsEqualTo(8.dp)
    }

    @Test
    fun list_header_fill_max_width_modifier() {
        composeTestRule.setContent {
            ListHeader(key = 1)
        }
        composeTestRule.onNodeWithTag("ListHeaderSurface").assertIsDisplayed().assert(
            SemanticsMatcher("has fillMaxWidth modifier") { semanticsNode ->
                semanticsNode.layoutInfo.getModifierInfo()
                    .find { it.modifier == Modifier.fillMaxWidth() } != null
            }
        )
    }
}