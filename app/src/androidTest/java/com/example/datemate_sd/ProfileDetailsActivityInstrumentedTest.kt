package com.example.datemate_sd

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.datemate_sd.ui.activity.ProfileDetailsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProfileDetailsActivityInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ProfileDetailsActivity::class.java)

    @Test
    fun testBackButtonClick() {
        onView(withId(R.id.backBtn))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun testTitleAndSubtitleDisplay() {
        onView(withId(R.id.addProfileHead1))
            .check(matches(isDisplayed()))
            .check(matches(withText("Add Profile Details")))

        onView(withId(R.id.addProfileHead2))
            .check(matches(isDisplayed()))
            .check(matches(withText("Please add your profile details here")))
    }

    @Test
    fun testProfileImageEditButtonClick() {
        onView(withId(R.id.profileImg))
            .check(matches(isDisplayed()))

        onView(withId(R.id.editBtn))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun testNameInputField() {
        val testName = "John Doe"

        onView(withId(R.id.nameInput))
            .check(matches(isDisplayed()))
            .perform(typeText(testName), closeSoftKeyboard())

        onView(withId(R.id.nameInput))
            .check(matches(withText(testName)))
    }

    @Test
    fun testUsernameInputField() {
        val testUsername = "johndoe123"

        onView(withId(R.id.usernameInput))
            .check(matches(isDisplayed()))
            .perform(typeText(testUsername), closeSoftKeyboard())

        onView(withId(R.id.usernameInput))
            .check(matches(withText(testUsername)))
    }

    @Test
    fun testPhoneInputField() {
        val testPhone = "1234567890"

        onView(withId(R.id.phoneInput))
            .check(matches(isDisplayed()))
            .perform(typeText(testPhone), closeSoftKeyboard())

        onView(withId(R.id.phoneInput))
            .check(matches(withText(testPhone)))
    }

    @Test
    fun testDateInputField() {
        val testDate = "01/01/1990"

        onView(withId(R.id.dateInput))
            .check(matches(isDisplayed()))
            .perform(typeText(testDate), closeSoftKeyboard())

        onView(withId(R.id.dateInput))
            .check(matches(withText(testDate)))
    }

    @Test
    fun testAddressSpinnerSelection() {
        val testAddress = "Kathmandu"

        onView(withId(R.id.addressSpinner))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withText(testAddress))
            .perform(click())


        onView(withId(R.id.addressSpinner))
            .check(matches(withSpinnerText(testAddress)))
    }

    @Test
    fun testContinueButtonClick() {
        onView(withId(R.id.continueBtnPD))
            .check(matches(isDisplayed()))
            .perform(click())


    }
}