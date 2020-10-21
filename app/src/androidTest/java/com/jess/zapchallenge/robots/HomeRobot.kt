package com.jess.zapchallenge.robots

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jess.zapchallenge.PropertiesAPI
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.view.MainActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.AllOf
import java.io.IOException
import java.io.InputStream

class HomeRobot(
    private val mockWebServer: MockWebServer?,
    private val activityTestRule: ActivityTestRule<MainActivity>
) {

    fun setRequest(): HomeRobot {
        mockWebServer?.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path?.contains(PropertiesAPI.SOURCE) == true) {
                    return MockResponse().setBody(
                        readFileFromAssets(
                            "response.json",
                            InstrumentationRegistry.getInstrumentation().context
                        )
                    )
                }

                return MockResponse().setBody(
                    readFileFromAssets(
                        "error_not_found.json",
                        InstrumentationRegistry.getInstrumentation().context
                    )
                )
            }
        }

        return this
    }

    private fun readFileFromAssets(fileName: String, c: Context): String {
        return try {
            val `is`: InputStream = c.assets.open(fileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun startActivity(): HomeRobot {
        activityTestRule.launchActivity(Intent(Intent.ACTION_MAIN))
        return this
    }

    fun finishActivity() {
        activityTestRule.finishActivity()
    }

    fun checkTextIsDisplayedOnRecyclerView(text: String) {
        onView(
            AllOf.allOf<View>(
                withText(text),
                isDescendantOfA(instanceOf(RecyclerView::class.java))
            )
        )
            .check(matches(isDisplayed()))
    }
}