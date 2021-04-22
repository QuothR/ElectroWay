package com.github.electroway

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception

class Session() {
    private val json = "application/json; charset=utf-8".toMediaType()
    private var token: String? = null
    private var url = "http://localhost:8090"
    private var client = OkHttpClient()

    internal constructor(
        url: String,
        client: OkHttpClient
    ) : this() {
        this.url = url
        this.client = client
    }

    fun signIn(username: String, password: String): Boolean {
        val body = JSONObject()
            .put("username", username)
            .put("password", password)
        val response = sendRequest(body, "/signin")
        if (response == null) {
            return false
        }
        token = response.toString()
        return false
    }

    fun signUp(username: String, password: String, email: String): Boolean {
        val body = JSONObject()
            .put("username", username)
            .put("password", password)
            .put("email", password)
        val response = sendRequest(body, "signup")
        if (response == null) {
            return false
        }
        token = response.toString()
        return false
    }

    private fun sendRequest(body: JSONObject, subpath: String): JSONObject? {
        val request = Request.Builder()
            .url(url + subpath)
            .post(body.toString().toRequestBody())
            .build()
        val response = client.newCall(request).execute()
        if (response.code != 200) {
            return null
        }
        return JSONObject(response.body!!.string())
    }

    private fun sendRequestWithToken(body: JSONObject, subpath: String): JSONObject? {
        val request = Request.Builder()
            .url(url + subpath)
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