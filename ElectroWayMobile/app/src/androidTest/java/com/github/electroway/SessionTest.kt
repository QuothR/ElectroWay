package com.github.electroway

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.internal.TlsUtil
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionTest {
    private val server = MockWebServer()
    private val clientCertificates = HandshakeCertificates.Builder()
        .addPlatformTrustedCertificates()
        .addInsecureHost(server.hostName)
        .build()
    private val client = OkHttpClient.Builder()
        .sslSocketFactory(clientCertificates.sslSocketFactory(), clientCertificates.trustManager)
        .build()

    init {
        server.useHttps(TlsUtil.localhost().sslSocketFactory(), false)
        server.enqueue(MockResponse().setBody("{}"))
    }

    @Test
    fun authenticate() {
        val session = Session("a", "b", server.url("/").toString(), client)
    }
}