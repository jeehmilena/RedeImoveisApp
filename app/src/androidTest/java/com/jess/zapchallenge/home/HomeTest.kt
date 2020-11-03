package com.jess.zapchallenge.home

import androidx.test.rule.ActivityTestRule
import com.jess.zapchallenge.PropertiesService
import com.jess.zapchallenge.home.view.MainActivity
import com.jess.zapchallenge.robots.HomeRobot
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeTest {
    private lateinit var robot: HomeRobot
    private var mockWebServer: MockWebServer? = null

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
}
