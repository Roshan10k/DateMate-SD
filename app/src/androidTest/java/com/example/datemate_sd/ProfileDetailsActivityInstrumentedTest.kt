package com.example.datemate_sd

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.datemate_sd.ui.activity.ProfileDetailsActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProfileDetailsActivityInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ProfileDetailsActivity::class.java)

    @Test
    fun testAllElementsDisplayed() {
        // Check if all key elements are displayed
        onView(withId(R.id.addProfileHead1)).check(matches(isDisplayed()))
        onView(withId(R.id.addProfileHead2)).check(matches(isDisplayed()))
        onView(withId(R.id.profileImg)).check(matches(isDisplayed()))
        onView(withId(R.id.ImageBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.NameInput)).check(matches(isDisplayed()))
        onView(withId(R.id.UsernameInput)).check(matches(isDisplayed()))
        onView(withId(R.id.PhoneInput)).check(matches(isDisplayed()))
        onView(withId(R.id.DateInput)).check(matches(isDisplayed()))
        onView(withId(R.id.AddressSpinner)).check(matches(isDisplayed()))
        onView(withId(R.id.continueBtnPD)).check(matches(isDisplayed()))
    }

    @Test
    fun testTitleAndSubtitleDisplay() {
        onView(withId(R.id.addProfileHead1))
            .check(matches(withText("Add Profile Details")))

        onView(withId(R.id.addProfileHead2))
            .check(matches(withText("Please add your profile details here")))
    }

    @Test
    fun testInputFieldsInteraction() {
        // Test Name Input
        val testName = "John Doe"
        onView(withId(R.id.NameInput))
            .perform(typeText(testName), closeSoftKeyboard())
            .check(matches(withText(testName)))

        // Test Username Input
        val testUsername = "johndoe123"
        onView(withId(R.id.UsernameInput))
            .perform(typeText(testUsername), closeSoftKeyboard())
            .check(matches(withText(testUsername)))

        // Test Phone Input
        val testPhone = "1234567890"
        onView(withId(R.id.PhoneInput))
            .perform(typeText(testPhone), closeSoftKeyboard())
            .check(matches(withText(testPhone)))
    }

    @Test
    fun testDatePickerInteraction() {
        // Open date picker
        onView(withId(R.id.DateInput)).perform(click())


    }

    @Test
    fun testProfileImageUpload() {

        onView(withId(R.id.ImageBtn)).perform(click())
    }


}