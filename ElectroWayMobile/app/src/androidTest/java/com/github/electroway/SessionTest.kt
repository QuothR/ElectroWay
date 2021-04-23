package com.github.electroway

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.internal.TlsUtil
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionTest {
    val session = Session()

    @Test
    fun authenticate() {
        val info = RegisterInfo(
            username = "username",
            password = "Password1!",
            firstName = "firstName",
            lastName = "lastName",
            phoneNumber = "phoneNumber",
            emailAddress = "electroway@mailinator.com",
            address1 = "address1",
            city = "city",
            country = "country",
            zipcode = "zipcode"
        )
        val response = session.register(info)
        Log.w("a", "fdsfsd")
        Log.w("a", "")
        Log.w("a", response)
    }
}