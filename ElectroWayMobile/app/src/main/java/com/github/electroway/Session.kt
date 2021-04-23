package com.github.electroway

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class Session() {
    private val json = "application/json; charset=utf-8".toMediaType()
    private var token: String? = null
    private var url = "http://192.168.1.7:8090/"
    private var client = OkHttpClient()

    internal constructor(
        url: String,
        client: OkHttpClient
    ) : this() {
        this.url = url
        this.client = client
    }

    fun register(info: RegisterInfo, callback: Callback) {
        sendRequest(info.getJson(), "register", callback)
    }

    fun login(info: LoginInfo, callback: Callback) {
        sendRequest(info.getJson(), "login", callback)
    }

    private fun sendRequest(body: JSONObject, subpath: String, callback: Callback) {
        val request = Request.Builder()
            .url(url + subpath)
            .post(body.toString().toRequestBody("application/json; charset=utf-8".toMediaType()))
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
    }
}