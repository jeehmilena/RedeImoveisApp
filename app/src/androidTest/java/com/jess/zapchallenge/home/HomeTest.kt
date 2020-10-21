package com.jess.zapchallenge.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.jess.zapchallenge.PropertiesService
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.view.MainActivity
import com.jess.zapchallenge.home.view.adapter.PropertiesAdapter
import com.jess.zapchallenge.robots.HomeRobot
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeTest {
    private lateinit var robot: HomeRobot
    private var mockWebServer: MockWebServer? = null
    private val LIST_ITEM_IN_TEST = 1

    init {
        mockWebServer = MockWebServer()
        PropertiesService.setBaseUrl(mockWebServer?.url("/").toString())
    }

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        robot = HomeRobot(mockWebServer, activityRule)
    }

    @After
    fun tearDown() {
        mockWebServer?.shutdown();
        robot.finishActivity()
    }

    @Test
    fun testIsListFragmentVisibleOnApplaunch() {
        robot.setRequest()
            .startActivity()
            .checkTextIsDisplayedOnRecyclerView("SALE")
    }

    @Test
    fun testSelectListItemisDetailFragmentVisible() {
        robot.setRequest()
        .startActivity()
        onView(withId(R.id.recyclerViewImoveis)).perform(
            actionOnItemAtPosition<PropertiesAdapter.ViewHolder>(
                LIST_ITEM_IN_TEST,
                click()
            )
        )

        onView(withId(R.id.imovel_type)).check(matches(withText("SALE")))
    }
//
//    @Test
//    fun testBacknavigationToListFragment() {
//        robot.startActivity()
//        onView(withId(R.id.recyclerViewImoveis)).perform(
//            actionOnItemAtPosition<PropertiesAdapter.ViewHolder>(
//                LIST_ITEM_IN_TEST,
//                click()
//            )
//        )
//
//        onView(withId(R.id.imovel_type)).check(matches(withText("SALE")))
//
//        pressBack()
//
//        onView(withId(R.id.recyclerViewImoveis)).check(matches(isDisplayed()))
//    }
}
