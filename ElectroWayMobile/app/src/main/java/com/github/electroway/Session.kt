package com.github.electroway

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception

class Session(username: String, password: String) {
    private val json = "application/json; charset=utf-8".toMediaType()
    private val token: String
    private var url = "https://test.com"
    private var client = OkHttpClient()

    internal constructor(
        username: String,
        password: String,
        url: String,
        client: OkHttpClient
    ) : this(username, password) {
        this.url = url
        this.client = client
    }

    init {
        val body = JSONObject()
            .put("username", username)
            .put("password", password)
            .toString()
            .toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.code != 200) {
            throw LoginFailed()
        }
        token = response.body!!.string()
    }

    private fun sendRequest(body: JSONObject): JSONObject? {
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .post(body.toString().toRequestBody())
            .build()
        val response = client.newCall(request).execute()
        if (response.code != 200) {
            return null
        }
        return JSONObject(response.body!!.string())
    }
}

class LoginFailed : Exception() {
}