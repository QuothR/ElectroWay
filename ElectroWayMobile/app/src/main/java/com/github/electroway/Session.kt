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
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*

class Session(val handler: Handler) {
    private val json = "application/json; charset=utf-8".toMediaType()
    private var token: String? = null
    private var client = OkHttpClient()
    private val expirationTime = 300_000L
    private var tokenRefreshTime = 0L
    private var loginInfo: LoginInfo? = null

    fun register(info: RegisterInfo, callback: (Boolean) -> Unit) {
        val request = Request.Builder()
            .url(
                HttpUrl.Builder().scheme("http").host("192.168.1.7").port(8090)
                    .addPathSegment("register").build()
            )
            .post(info.getJson().toString().toRequestBody(json))
            .build()
        client.newCall(request).enqueue(object : Callback {
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
        val request = Request.Builder()
            .url(
                HttpUrl.Builder().scheme("http").host("192.168.1.7").port(8090)
                    .addPathSegment("login").build()
            )
            .post(info.getJson().toString().toRequestBody(json))
            .build()
        client.newCall(request).enqueue(object : Callback {
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

    fun userInfo(callback: (JSONObject?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                HttpUrl.Builder().scheme("http").host("192.168.1.7").port(8090)
                    .addPathSegment("user").build()
            )
            .header("Authorization", "Bearer " + token!!)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = JSONObject(response.body!!.string())
                    handler.post {
                        callback(json)
                    }
                } else {
                    handler.post {
                        callback(null)
                    }
                }
            }
        })
    }

    fun addStation(info: AddStationInfo, callback: (Boolean) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                HttpUrl.Builder().scheme("http").host("192.168.1.7").port(8090)
                    .addPathSegment("station").build()
            )
            .header("Authorization", "Bearer " + token!!)
            .post(info.getJson().toString().toRequestBody(json))
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val success = response.isSuccessful
                handler.post {
                    callback(success)
                }
            }
        })
    }

    fun getStations(callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                HttpUrl.Builder().scheme("http").host("192.168.1.7").port(8090)
                    .addPathSegment("station").build()
            )
            .header("Authorization", "Bearer " + token!!)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = JSONArray(response.body!!.string())
                    handler.post {
                        callback(json)
                    }
                } else {
                    handler.post {
                        callback(null)
                    }
                }
            }
        })
    }

    private fun refreshTokenIfNeeded() {
        if (System.currentTimeMillis() - tokenRefreshTime >= expirationTime) {
            login(loginInfo!!) {}
        }
    }
}