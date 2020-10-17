package com.jess.zapchallenge.robots

import android.content.Context
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jess.zapchallenge.PropertiesAPI
import com.jess.zapchallenge.home.view.MainActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.IOException
import java.io.InputStream

class HomeRobot(
    private val mockWebServer: MockWebServer,
    private val activityTestRule: ActivityTestRule<MainActivity>
) {

    fun startActivity() {
        activityTestRule.launchActivity(Intent(Intent.ACTION_MAIN))
    }

    fun finishActivity() {
        activityTestRule.finishActivity()
    }

    fun setRequest(): HomeRobot {
        mockWebServer.dispatcher = object : Dispatcher() {
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
}