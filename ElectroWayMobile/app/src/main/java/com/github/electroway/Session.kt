package com.github.electroway

import android.os.Handler
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.security.cert.X509Certificate
import java.time.Duration
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class Session(val handler: Handler) {
    private val json = "application/json; charset=utf-8".toMediaType()
    private var token: String? = null
    private val client: OkHttpClient

    companion object {
        private const val expirationTime = 300_000L
        private val timeout = Duration.ofMinutes(2)
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
            .callTimeout(timeout)
            .connectTimeout(timeout)
            .readTimeout(timeout)
            .writeTimeout(timeout)
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
            .header("Authorization", "Bearer $token")
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
            .header("Authorization", "Bearer $token")
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
            .header("Authorization", "Bearer $token")
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

    fun getAllStations(callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(buildBasePath().addPathSegment("station").addPathSegment("all").build())
            .header("Authorization", "Bearer $token")
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

    fun getStation(id: Int, callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(buildBasePath().addPathSegment("station").addPathSegment("all").build())
            .header("Authorization", "Bearer $token")
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
            .header("Authorization", "Bearer $token")
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

    fun findRoute(findRouteInfo: FindRouteInfo, callback: (JSONObject?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(buildBasePath().addPathSegment("routing").build())
            .header("Authorization", "Bearer $token")
            .post(findRouteInfo.getJson().toString().toRequestBody(json))
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
                    Log.e("a", response.body!!.string())
                    handler.post {
                        callback(null)
                    }
                }
            }
        })
    }

    private fun refreshTokenIfNeeded() {
        if (System.currentTimeMillis() - tokenRefreshTime >= expirationTime) {
            loginInfo?.let { login(it) {} }
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
            .header("Authorization", "Bearer $token")
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
            .header("Authorization", "Bearer $token")
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
            .header("Authorization", "Bearer $token")
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

    fun addChargingPlug(
        station: Int,
        chargingPoint: Int,
        chargingPlug: ChargingPlugInfo,
        callback: (JSONObject?) -> Unit
    ) {
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
            .header("Authorization", "Bearer $token")
            .post(chargingPlug.getJson().toString().toRequestBody(json))
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

    fun getChargingPlugs(station: Int, chargingPoint: Int, callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(chargingPoint.toString())
                    .addPathSegment("plugs")
                    .build()
            )
            .header("Authorization", "Bearer $token")
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

    fun removeChargingPlug(
        station: Int,
        chargingPoint: Int,
        plug: Int,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(chargingPoint.toString())
                    .addPathSegment("plugs")
                    .addPathSegment(plug.toString()).build()
            )
            .header("Authorization", "Bearer $token")
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

    fun updateChargingPlug(
        station: Int,
        chargingPoint: Int,
        chargingPlug: ChargingPlugInfo,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(chargingPoint.toString())
                    .addPathSegment("plugs")
                    .addPathSegment(chargingPlug.id.toString()).build()
            )
            .header("Authorization", "Bearer $token")
            .put(chargingPlug.getJson().toString().toRequestBody(json))
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

    fun addReview(
        station: Int,
        addReviewInfo: AddReviewInfo,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("review")
                    .addPathSegment("create")
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .build()
            )
            .header("Authorization", "Bearer $token")
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
                    .addPathSegment("review")
                    .addPathSegment("all")
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .build()
            )
            .header("Authorization", "Bearer $token")
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
        addCarInfo: CarInfo,
        callback: (Boolean) -> Unit
    ) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("car")
                    .addPathSegment("create").build()
            )
            .header("Authorization", "Bearer $token")
            .post(addCarInfo.getJson().toString().toRequestBody(json))
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val success = response.isSuccessful
                Log.e("a", response.body!!.string())
                handler.post {
                    callback(success)
                }
            }
        })
    }

    fun updateCar(
        addCarInfo: CarInfo,
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
            .header("Authorization", "Bearer $token")
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
            .header("Authorization", "Bearer $token")
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
            .header("Authorization", "Bearer $token")
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

    fun forgotPassword(email: String, callback: (Boolean) -> Unit) {
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

    fun getTemplateCars(callback: (JSONArray?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("templatecar")
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

    fun isFavourite(station: Int, callback: (Int?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("favourite")
                    .addPathSegment("all")
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .build()
            )
            .header("Authorization", "Bearer $token")
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = JSONArray(response.body!!.string())
                    if (json.length() > 0) {
                        handler.post {
                            callback(json.getJSONObject(0).getInt("id"))
                        }
                        return
                    }
                }
                handler.post {
                    callback(null)
                }
            }
        })
    }

    fun deleteFavourite(station: Int, favourite: Int, callback: (Boolean) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("favourite")
                    .addPathSegment("delete")
                    .addPathSegment(favourite.toString())
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .build()
            )
            .header("Authorization", "Bearer $token")
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


    fun createFavourite(station: Int, callback: (Boolean) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("favourite")
                    .addPathSegment("create")
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .build()
            )
            .header("Authorization", "Bearer $token")
            .post(JSONObject().toString().toRequestBody(json))
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

    fun pay(station: Int, point: Int, plug: Int, totalKw: Int, callback: (String?) -> Unit) {
        refreshTokenIfNeeded()
        val request = Request.Builder()
            .url(
                buildBasePath()
                    .addPathSegment("station")
                    .addPathSegment(station.toString())
                    .addPathSegment("points")
                    .addPathSegment(point.toString())
                    .addPathSegment("plugs")
                    .addPathSegment(plug.toString())
                    .addPathSegment("pay")
                    .build()
            )
            .header("Authorization", "Bearer $token")
            .post(
                JSONObject().put("totalKW", totalKw).put("description", "ElectroWay payment")
                    .toString().toRequestBody(json)
            )
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val response = response.body!!.string()
                    val url = Regex("https://[^\\s]+").find(response)
                    if (url != null) {
                        handler.post {
                            callback(url.value)
                        }
                        return
                    }
                }
                handler.post {
                    callback(null)
                }
            }
        })
    }

    private fun buildBasePath(): HttpUrl.Builder {
        return HttpUrl.Builder().scheme("https").host("192.168.1.7").port(443)
    }
}