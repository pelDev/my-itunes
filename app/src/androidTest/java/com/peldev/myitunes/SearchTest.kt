package com.peldev.myitunes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTest {

    @Rule @JvmField
    val mainActivity =
            ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun searchForSong() {
        val artiste = "eminem"

        // type artiste name into edit text
        onView(withId(R.id.et_artiste_name)).perform(typeText(artiste))

        // click on search button
        onView(withId(R.id.search_button)).perform(click())


    }

}