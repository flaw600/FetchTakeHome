package com.example.fetchtakehome

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.example.fetchtakehome.composable.HiringList
import com.example.fetchtakehome.model.HiringCandidate
import org.junit.Rule
import org.junit.Test

class HiringListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getListIdString(id: Int) = composeTestRule.activity.getString(R.string.list_header, id)
    private fun getItemIdString(id: Int) = composeTestRule.activity.getString(R.string.item_id, id)

    @Test
    fun hiring_list_single_group() {
        val item = HiringCandidate(1, 1, "Item 1")
        val groupOfCandidatesByListID = mapOf(1 to listOf(item))
        composeTestRule.setContent {
            HiringList(groupOfCandidatesByListID)
        }

        composeTestRule.onNodeWithText("Item 1", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasAnySibling(hasText(getItemIdString(1))))
    }

    @Test
    fun hiring_list_many_items_in_group_appear_in_sequence() {
        val items = mutableListOf<HiringCandidate>()
        for(i in 1..10){
            items.add(HiringCandidate(i,1,"Item $i"))
        }
        val groupOfCandidatesByListID = mapOf(1 to items)
        composeTestRule.setContent {
            HiringList(groupOfCandidatesByListID)
        }

        composeTestRule.onNodeWithText("Item 1", useUnmergedTree = true).assertIsDisplayed()
            .assert(hasAnySibling(hasText(getItemIdString(1))))
        composeTestRule.onNodeWithText("Item 10", useUnmergedTree = true).performScrollTo()
            .assert(hasAnySibling(hasText(getItemIdString(10))))
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
            hasAnyChild(hasText(getListIdString(1), true))
        ).assertIsDisplayed()
         .assert(hasAnySibling(hasAnyChild(hasText(getListIdString(2)))))

        //test that name and item # are headline and trailing content, respectively
        composeTestRule.onNodeWithText("Item 1", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasAnySibling(hasText(getItemIdString(1))))

        composeTestRule.onNodeWithText("Item 2", useUnmergedTree = true)
            .performScrollTo()
            .assert(hasAnySibling(hasText(getItemIdString(2))))
    }
}