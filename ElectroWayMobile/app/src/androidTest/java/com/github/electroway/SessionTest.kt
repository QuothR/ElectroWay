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
    private val clientCertificates = HandshakeCertificates.Builder()
        .addPlatformTrustedCertificates()
        .addInsecureHost("http://10.0.2.2:8090")
        .build()
    private val client = OkHttpClient.Builder()
        .sslSocketFactory(clientCertificates.sslSocketFactory(), clientCertificates.trustManager)
        .build()

    @Test
    fun authenticate() {
        val request = Request.Builder()
            .url("http://10.0.2.2:8090/")
            .build()
        val response = client.newCall(request).execute()
        Log.w("a", response.body!!.string())
    }
}