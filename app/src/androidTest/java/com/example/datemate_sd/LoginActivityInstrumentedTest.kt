package com.example.datemate_sd

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.datemate_sd.ui.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityInstrumentedTest {

    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginUI() {
        // Enter email in the EmailView EditText
        onView(withId(R.id.EmailView)).perform(
            typeText("test@example.com")
        )

        // Enter password in the passwordView EditText
        onView(withId(R.id.passwordView)).perform(
            typeText("password123")
        )

        // Close the soft keyboard
        closeSoftKeyboard()

        // Simulate a delay to observe the UI (optional)
        Thread.sleep(1000)

        // Click the login button
        onView(withId(R.id.loginButton)).perform(
            click()
        )

        // Simulate a delay to observe the UI (optional)
        Thread.sleep(1000)

        // Verify the app name text is displayed correctly
        onView(withId(R.id.appName)).check(matches(withText("Date Mate")))

        // Verify the "Don't have an account? Sign Up" button text
        onView(withId(R.id.SignupBtn)).check(matches(withText("Don't have an account? Sign Up")))

        // Verify the "Forget Password?" button text
        onView(withId(R.id.forgetbtn)).check(matches(withText("Forget Password?")))
    }
}