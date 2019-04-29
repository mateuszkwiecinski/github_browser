package pl.mkwiecinski.presentation.list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import pl.mkwiecinski.presentation.R
import pl.mkwiecinski.presentation.list.ui.ListFragment

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListFragmentTest {

    @Test
    fun testSmokeTest() {
        val scenario = launchFragmentInContainer<ListFragment>(themeResId = R.style.AppTheme)
        scenario.onFragment {
            onView(withId(android.R.id.content)).check(matches(isDisplayed()))
        }
    }
}
