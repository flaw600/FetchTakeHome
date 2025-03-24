package com.example.fetchtakehome.composable

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import com.example.fetchtakehome.HiringTest
import com.example.fetchtakehome.R
import com.example.fetchtakehome.model.HiringCandidate
import org.junit.Test

class HiringCellTest : HiringTest() {

    private fun getItemIdString(id: Int) = composeTestRule.activity.getString(R.string.item_id, id)

    @Test
    fun test_hiring_cell_headline_and_id_display() {
        val item = HiringCandidate(1, 1, "Item 1")
        val groupOfCandidatesByListID = mapOf(1 to listOf(item))
        composeTestRule.setContent {
            HiringList(groupOfCandidatesByListID)
        }

        composeTestRule.onNodeWithText("Item 1", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasAnySibling(hasText(getItemIdString(1))))
    }
}