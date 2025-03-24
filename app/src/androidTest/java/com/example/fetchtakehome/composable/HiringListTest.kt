package com.example.fetchtakehome.composable

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.example.fetchtakehome.HiringTest
import com.example.fetchtakehome.model.HiringCandidate
import org.junit.Test

class HiringListTest : HiringTest() {

    @Test
    fun hiring_list_single_group() {
        val item = HiringCandidate(1, 1, "Item 1")
        val groupOfCandidatesByListID = mapOf(1 to listOf(item))
        composeTestRule.setContent {
            HiringList(groupOfCandidatesByListID)
        }

        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
    }

    @Test
    fun hiring_list_many_items_in_group_appear_in_sequence() {
        val items = mutableListOf<HiringCandidate>()
        for(i in 1..10){
            items.add(HiringCandidate(i, 1, "Item $i"))
        }
        val groupOfCandidatesByListID = mapOf(1 to items)
        composeTestRule.setContent {
            HiringList(groupOfCandidatesByListID)
        }

        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 10").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun hiring_list_multiple_groups_appear_with_correct_headers() {
        val itemGroup1 = HiringCandidate(1, 1, "Item 1")
        val itemGroup2 = HiringCandidate(2, 2, "Item 2")
        val groupOfCandidatesByListID = mapOf(
            1 to listOf(itemGroup1),
            2 to listOf(itemGroup2)
        )
        composeTestRule.setContent {
            HiringList(groupOfCandidatesByListID)
        }

        //test sticky headers are siblings
        composeTestRule.onNode(
           hasTestTag("ListHeaderSurface") and hasAnyChild(hasText(getListIdString(1), true))
        ).assertIsDisplayed()
         .assert(
             hasAnySibling(
                 hasTestTag("ListHeaderSurface")
                         and hasAnyChild(hasText(getListIdString(2), true))
             )
         )

        //test that name and item # are headline and trailing content, respectively
        composeTestRule.onNodeWithText("Item 1")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Item 2")
            .performScrollTo()
            .assertIsDisplayed()
    }
}