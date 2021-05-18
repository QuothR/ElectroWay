package com.github.electroway

import android.os.Handler
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class Session(val handler: Handler) {
    private val json = "application/json; charset=utf-8".toMediaType()
    private var token: String? = null
    private val client: OkHttpClient

    companion object {
        private const val expirationTime = 300_000L
    }

    private var tokenRefreshTime = 0L
    private var loginInfo: LoginInfo? = null

    init {
        val trustAllCerts = arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory
        client = OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }.build()
    }

    fun register(info: RegisterInfo, callback: (Boolean) -> Unit) {
        val request = Request.Builder()
            .url(buildBasePath().addPathSegment("register").build())
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
            .url(buildBasePath().addPathSegment("login").build())
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
            .url(buildBasePath().addPathSegment("user").build())
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

    fun addStation(info: AddStationInfo, callback: (JSONObject?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(buildBasePath().addPathSegment("station").build())
            .header("Authorization", "Bearer " + token!!)
            .post(info.getJson().toString().toRequestBody(json))
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

    fun getStations(callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(buildBasePath().addPathSegment("station").build())
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

    fun removeStation(station: Int, callback: (Boolean) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString()).build()
            )
            .header("Authorization", "Bearer " + token!!)
            .delete()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    handler.post {
                        callback(true)
                    }
                } else {
                    handler.post {
                        callback(false)
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

    fun addChargingPoint(station: Int, callback: (JSONObject?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points").build()
            )
            .header("Authorization", "Bearer " + token!!)
            .post("".toRequestBody())
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

    fun getChargingPoints(station: Int, callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .build()
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

    fun removeChargingPoint(station: Int, chargingPoint: Int, callback: (Boolean) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(chargingPoint.toString())
                    .addPathSegment(station.toString()).build()
            )
            .header("Authorization", "Bearer " + token!!)
            .delete()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    handler.post {
                        callback(true)
                    }
                } else {
                    handler.post {
                        callback(false)
                    }
                }
            }
        })
    }

    fun addPlug(station: Int, chargingPoint: Int, callback: (JSONObject?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(chargingPoint.toString())
                    .build()
            )
            .header("Authorization", "Bearer " + token!!)
            .post("".toRequestBody())
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

    fun getPlugs(station: Int, chargingPoint: Int, callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(chargingPoint.toString())
                    .build()
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

    fun removePlug(station: Int, chargingPoint: Int, plug: Int, callback: (Boolean) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(chargingPoint.toString())
                    .addPathSegment("plugs")
                    .addPathSegment(plug.toString())
                    .addPathSegment(station.toString()).build()
            )
            .header("Authorization", "Bearer " + token!!)
            .delete()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    handler.post {
                        callback(true)
                    }
                } else {
                    handler.post {
                        callback(false)
                    }
                }
            }
        })
    }

    fun addReview(
        station: Int,
        addReviewInfo: AddReviewInfo,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString()).build()
            )
            .header("Authorization", "Bearer " + token!!)
            .post(addReviewInfo.getJson().toString().toRequestBody(json))
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

    fun getReviews(station: Int, callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("reviews")
                    .build()
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

    fun addCar(
        addCarInfo: AddCarInfo,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("car")
                    .addPathSegment("create").build()
            )
            .header("Authorization", "Bearer " + token!!)
            .post(addCarInfo.getJson().toString().toRequestBody(json))
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

    fun updateCar(
        addCarInfo: AddCarInfo,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("car")
                    .addPathSegment("update")
                    .addPathSegment(addCarInfo.id.toString()).build()
            )
            .header("Authorization", "Bearer " + token!!)
            .put(addCarInfo.getJson().toString().toRequestBody(json))
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

    fun removeCar(
        id: Int,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("car")
                    .addPathSegment("delete")
                    .addPathSegment(id.toString()).build()
            )
            .header("Authorization", "Bearer " + token!!)
            .delete()
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

    fun getCars(callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("car")
                    .addPathSegment("all")
                    .build()
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

    fun forgetPassword(email: String, callback: (Boolean) -> Unit) {
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("forgot_password")
                    .addQueryParameter("email", email)
                    .build()
            )
            .post("".toRequestBody())
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

    private fun buildBasePath(): HttpUrl.Builder {
        return HttpUrl.Builder().scheme("https").host("192.168.1.7").port(443)
    }
}