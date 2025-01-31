package com.example.gym_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.gym_app.DummyGymsList.getDummyGymsList
import com.example.gym_app.gyms.SemanticsDescription
import com.example.gym_app.gyms.presentation.gym_screen.view.GymsScreen
import com.example.gym_app.ui.theme.Gym_AppTheme
import org.junit.Rule
import org.junit.Test

class GymsScreenTest{
    @get: Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive()
    {
        testRule.setContent {
            Gym_AppTheme{
                GymsScreen(state= GymsScrenState(
                    gyms = emptyList(),
                    isLoading = true,
                    error = null
                ),onItemClick= {},onFavoriteIconClick= {_:Int , _:Boolean -> })
            }
        }

        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING).assertIsDisplayed()
    }

    @Test
    fun loadedContentState_isActive()
    {
        val gymsList = getDummyGymsList()

        testRule.setContent {
            Gym_AppTheme{
                GymsScreen(state= GymsScrenState(
                    gyms = gymsList,
                    isLoading = false,
                    error = null
                ),onItemClick= {},onFavoriteIconClick= {_:Int , _:Boolean -> })
            }
        }

        testRule.onNodeWithText(gymsList[0].name).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING).assertDoesNotExist()
    }

    @Test
    fun errorState_isActive()
    {
        val errorText = "Failed to load data"

        testRule.setContent {
            Gym_AppTheme{
                GymsScreen(state= GymsScrenState(
                    gyms = emptyList(),
                    isLoading = false,
                    error = errorText
                ),onItemClick= {},onFavoriteIconClick= {_:Int , _:Boolean -> })
            }
        }

        testRule.onNodeWithText(errorText).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING).assertDoesNotExist()
    }

    @Test
    fun onItemClick_idPassedCorrectly()
    {
        val gymsList = getDummyGymsList()
        val gymItem = gymsList[0]
        testRule.setContent {
            Gym_AppTheme{
                GymsScreen(state= GymsScrenState(
                    gyms = gymsList,
                    isLoading = false,
                    error = null
                ),onItemClick= { id ->
                    assert(id == gymItem.id)
                },onFavoriteIconClick= {_:Int , _:Boolean -> })
            }
        }

        testRule.onNodeWithText(gymItem.name).performClick()
    }
}