package com.github.electroway

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

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

    fun userInfo(callback: Callback) {
        sendAuthRequest("user", callback)
    }

    private fun sendRequest(body: JSONObject, subpath: String, callback: Callback) {
        val request = Request.Builder()
            .url(url + subpath)
            .post(body.toString().toRequestBody(json))
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
    }

    private fun sendAuthRequest(subpath: String, callback: Callback) {
        val request = Request.Builder()
            .url(url + subpath)
            .header("Authorization", "Bearer " + token!!)
            .get()
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
    }

    fun changeToken(body: String) {
        token = JSONObject(body).getString("token")
    }
}