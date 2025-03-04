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












}