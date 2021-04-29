package com.github.electroway

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.*

class Session(val handler: Handler) {
    private val json = "application/json; charset=utf-8".toMediaType()
    private var token: String? = null
    private var url = "http://192.168.1.7:8090/"
    private var client = OkHttpClient()
    private val expirationTime = 300_000L
    private var tokenRefreshTime = 0L
    private var loginInfo: LoginInfo? = null
//    private val refreshHandler = Handler(Looper.getMainLooper())
//    private val refreshRunner = Runnable {
//        login()
//    }

//    internal constructor(
//        url: String,
//        client: OkHttpClient
//    ) : this() {
//        this.url = url
//        this.client = client
//    }

    fun register(info: RegisterInfo, callback: (Boolean) -> Unit) {
        sendRequest(info.getJson(), "register", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                tokenRefreshTime = System.currentTimeMillis()
                val success = response.isSuccessful
                handler.post {
                    callback(success)
                }
            }
        })
    }

    fun login(info: LoginInfo, callback: (Boolean) -> Unit) {
        loginInfo = info
        sendRequest(info.getJson(), "login", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val success = response.isSuccessful
                val body = response.body!!.string()
                if (success) {
                    token = JSONObject(body).getString("token")
                }
                handler.post {
                    callback(success)
                }
            }
        })
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
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(url + subpath)
            .header("Authorization", "Bearer " + token!!)
            .get()
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
    }

    private fun refreshTokenIfNeeded() {
        if (System.currentTimeMillis() - tokenRefreshTime >= expirationTime) {
            login(loginInfo!!) {}
        }
    }
}