package com.example.datemate_sd

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.datemate_sd.ui.activity.IdealMatchPage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class IdealMatchPageInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(IdealMatchPage::class.java)

    @Test
    fun testBackArrowClick() {
        onView(withId(R.id.backArrow))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun testLoveOptionSelection() {
        onView(withId(R.id.loveOption))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun testFriendsOptionSelection() {
        onView(withId(R.id.friendsOption))
            .check(matches(isDisplayed()))
            .perform(click())

    }






}