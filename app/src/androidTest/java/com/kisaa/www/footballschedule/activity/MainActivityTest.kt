package com.kisaa.www.footballschedule.activity

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kisaa.www.footballschedule.EspressoIdlingResources
import com.kisaa.www.footballschedule.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource)
    }

    @Test
    fun searchViewBehaviour(){
        onView(withId(R.id.league_list)).check(matches(isDisplayed()))
        onView(withId(R.id.league_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("Arsenal vs Chelsea"), pressImeActionButton())

        onView(withId(R.id.rv_search))
            .check(matches(hasDescendant(withText("Arsenal"))))
        onView(withId(R.id.rv_search))
            .check(matches(hasDescendant(withText("Chelsea")))).perform(pressBack())

        onView(isAssignableFrom(EditText::class.java)).
            perform(clearText(),typeText("PSM vs Arema"), pressImeActionButton())
        onView(withText(R.string.pertandingan_tidak_ditemukan)).check(matches(isDisplayed()))
    }
}