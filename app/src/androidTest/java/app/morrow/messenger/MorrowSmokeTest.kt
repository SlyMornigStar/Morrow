package app.morrow.messenger

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class MorrowSmokeTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun chatShellShowsBrandAndPrimaryNavigation() {
        compose.onNodeWithText("morrow").assertIsDisplayed()
        compose.onNodeWithText("Chats").assertIsDisplayed()
        compose.onNodeWithText("Contacts").assertIsDisplayed()
        compose.onNodeWithText("Calls").assertIsDisplayed()
        compose.onNodeWithText("Settings").assertIsDisplayed()
        compose.onNodeWithText("TDLib not connected").assertIsDisplayed()
    }
}
