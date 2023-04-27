package com.example.databinding_assignment

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun startButton_onClick_startsStopwatchTimer() {
        onView(withId(R.id.start_button)).perform(click())
        onView(isRoot()).perform(delayUi(3500))

        onView(withId(R.id.stopwatch_textview))
            .check(matches(withText("00:00:03")))
    }

    @Test
    fun resetButton_onClick_resetsStopwatchTimer() {
        onView(withId(R.id.start_button)).perform(click())
        onView(isRoot()).perform(delayUi(3500))

        onView(withId(R.id.reset_button)).perform(click())

        onView(withId(R.id.stopwatch_textview))
            .check(matches(withText("00:00:00")))
    }

    @Test
    fun stopButton_onClick_stopsStopwatchTimer() {
        onView(withId(R.id.start_button)).perform(click())
        onView(isRoot()).perform(delayUi(3500))

        onView(withId(R.id.stop_button)).perform(click())

        onView(withId(R.id.stopwatch_textview))
            .check(matches(withText("00:00:03")))
    }

    private fun delayUi(delayMilliSeconds: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = "delay for $delayMilliSeconds milliseconds"

            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delayMilliSeconds)
            }
        }
    }

}