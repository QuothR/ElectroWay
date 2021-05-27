package com.github.electroway.network

import android.os.Handler
import com.github.electroway.*
import com.github.electroway.models.*
import com.github.electroway.network.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.time.Duration

class Session(val handler: Handler) {
    private val json = "application/json; charset=utf-8".toMediaType()
    private var token: String? = null
    private val client =
        OkHttpClient.Builder().callTimeout(timeout).connectTimeout(timeout).readTimeout(timeout)
            .writeTimeout(timeout).build()

    companion object {
        private const val expirationTime = 300_000L
        private val timeout = Duration.ofMinutes(2)
    }

    private var tokenRefreshTime = 0L
    private var loginInfo: Login? = null

    private fun <T> call(
        method: Method,
        callback: (T) -> Unit,
        path: Array<String>,
        queryParams: Array<Pair<String, String>> = emptyArray(),
        useToken: Boolean = true,
        process: (Response) -> T,
    ) {
        val url = buildBasePath()
        for (segment in path) {
            url.addPathSegment(segment)
        }
        for (queryParam in queryParams) {
            url.addQueryParameter(queryParam.first, queryParam.second)
        }
        val request = Request.Builder().url(url.build())
        when (method) {
            is Get -> request.get()
            is Post -> request.post(method.body.toRequestBody(json))
            is Put -> request.put(method.body.toRequestBody(json))
            is Delete -> request.delete()
        }
        if (useToken) {
            refreshTokenIfNeeded()
            request.header("Authorization", "Bearer $token")
        }
        client.newCall(request.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val result = process(response)
                handler.post {
                    callback(result)
                }
            }
        })
    }

    private fun buildBasePath(): HttpUrl.Builder {
        return HttpUrl.Builder().scheme("https").host("electroway.herokuapp.com").port(443)
    }

    private fun refreshTokenIfNeeded() {
        if (System.currentTimeMillis() - tokenRefreshTime >= expirationTime) {
            loginInfo?.let { login(it) {} }
        }
    }

    fun register(info: Register, callback: (Boolean) -> Unit) =
        call(
            Post(info.getJson().toString()),
            callback,
            arrayOf("register"),
            useToken = false
        ) { return@call it.isSuccessful }

    fun login(info: Login, callback: (Boolean) -> Unit) =
        call(Post(info.getJson().toString()), callback, arrayOf("login"), useToken = false) {
            if (!it.isSuccessful) return@call false
            token = JSONObject(it.body!!.string()).getString("token")
            val request = Request.Builder()
                .url(buildBasePath().addPathSegment("user").build())
                .header("Authorization", "Bearer $token")
                .get()
                .build()
            tokenRefreshTime = System.currentTimeMillis()
            val response = client.newCall(request).execute()
            val obj = JSONObject(response.body!!.string())
            if (obj.getJSONArray("roles").length() == 0) {
                for (role in arrayOf("ROLE_OWNER", "ROLE_DRIVER")) {
                    val request = Request.Builder().url(
                        buildBasePath()
                            .addPathSegment("user")
                            .addPathSegment("addrole")
                            .addQueryParameter("roleName", role)
                            .build()
                    ).header("Authorization", "Bearer $token")
                        .post("".toRequestBody(json)).build()
                    client.newCall(request).execute()
                }
            }
            return@call true
        }

    fun updateWallet(wallet: Wallet, callback: (Boolean) -> Unit) = call(
        Put(wallet.getJson().toString()),
        callback,
        arrayOf("user", "wallet")
    ) { return@call it.isSuccessful }

    fun userInfo(callback: (JSONObject?) -> Unit) = call(
        Get,
        callback,
        arrayOf("user")
    ) { if (it.isSuccessful) JSONObject(it.body!!.string()) else null }

    fun addStation(info: AddStation, callback: (JSONObject?) -> Unit) =
        call(
            Post(info.getJson().toString()),
            callback,
            arrayOf("station")
        ) { if (it.isSuccessful) JSONObject(it.body!!.string()) else null }

    fun getStations(callback: (JSONArray?) -> Unit) = call(
        Get,
        callback,
        arrayOf("station")
    ) { if (it.isSuccessful) JSONArray(it.body!!.string()) else null }

    fun getAllStations(callback: (JSONArray?) -> Unit) = call(
        Get,
        callback,
        arrayOf("station", "all")
    ) { return@call if (it.isSuccessful) JSONArray(it.body!!.string()) else null }

    fun getAllPlugs(station: Int, callback: (List<Pair<Int, ChargingPlug>>) -> Unit) =
        call(Get, callback, arrayOf("station", station.toString(), "points")) {
            val points = JSONArray(it.body!!.string())
            val result = mutableListOf<Pair<Int, ChargingPlug>>()
            for (i in 0 until points.length()) {
                val pointId = points.getJSONObject(i).getInt("id")
                val request = Request.Builder()
                    .url(
                        buildBasePath()
                            .addPathSegment("station")
                            .addPathSegment(station.toString())
                            .addPathSegment("points")
                            .addPathSegment(pointId.toString())
                            .addPathSegment("plugs").build()
                    )
                    .header("Authorization", "Bearer $token")
                    .get()
                    .build()
                val response = client.newCall(request).execute()
                val plugs = JSONArray(response.body!!.string())
                for (i in 0 until plugs.length()) {
                    val plug = plugs.getJSONObject(i)
                    val id = plug.getInt("id")
                    val connectorType = plug.getString("connectorType")
                    val priceKw = plug.getDouble("priceKw")
                    val chargingSpeedKw = plug.getDouble("chargingSpeedKw")
                    result.add(
                        Pair(
                            pointId,
                            ChargingPlug(
                                id,
                                connectorType,
                                priceKw,
                                chargingSpeedKw
                            )
                        )
                    )
                }
            }
            return@call result
        }

    fun removeStation(station: Int, callback: (Boolean) -> Unit) = call(
        Delete,
        callback,
        arrayOf("station", station.toString())
    ) { return@call it.isSuccessful }

    fun findRoute(findRouteInfo: FindRoute, callback: (JSONObject?) -> Unit) = call(
        Post(findRouteInfo.getJson().toString()),
        callback,
        arrayOf("routing")
    ) { return@call if (it.isSuccessful) JSONObject(it.body!!.string()) else null }

    fun addChargingPoint(station: Int, callback: (JSONObject?) -> Unit) =
        call(
            Post(""),
            callback,
            arrayOf("station", station.toString(), "points")
        ) { return@call if (it.isSuccessful) JSONObject(it.body!!.string()) else null }

    fun getChargingPoints(station: Int, callback: (JSONArray?) -> Unit) =
        call(
            Get,
            callback,
            arrayOf("station", station.toString(), "points")
        ) { if (it.isSuccessful) JSONArray(it.body!!.string()) else null }

    fun removeChargingPoint(station: Int, chargingPoint: Int, callback: (Boolean) -> Unit) = call(
        Delete,
        callback,
        arrayOf(
            "station",
            station.toString(),
            "points",
            chargingPoint.toString(),
            station.toString(),
            station.toString()
        )
    ) { return@call it.isSuccessful }

    fun addChargingPlug(
        station: Int,
        chargingPoint: Int,
        chargingPlug: ChargingPlug,
        callback: (JSONObject?) -> Unit
    ) = call(
        Post(chargingPlug.getJson().toString()),
        callback,
        arrayOf("station", station.toString(), "points", chargingPoint.toString())
    ) { return@call if (it.isSuccessful) JSONObject(it.body!!.string()) else null }

    fun getChargingPlugs(station: Int, chargingPoint: Int, callback: (JSONArray?) -> Unit) = call(
        Get,
        callback,
        arrayOf("station", station.toString(), "points", chargingPoint.toString(), "plugs")
    ) { return@call if (it.isSuccessful) JSONArray(it.body!!.string()) else null }

    fun removeChargingPlug(
        station: Int,
        chargingPoint: Int,
        plug: Int,
        callback: (Boolean) -> Unit
    ) = call(
        Delete,
        callback,
        arrayOf(
            "station",
            station.toString(),
            "points",
            chargingPoint.toString(),
            "plugs",
            plug.toString()
        )
    ) { return@call it.isSuccessful }

    fun updateChargingPlug(
        station: Int,
        chargingPoint: Int,
        chargingPlug: ChargingPlug,
        callback: (Boolean) -> Unit
    ) = call(
        Put(chargingPlug.getJson().toString()),
        callback,
        arrayOf(
            "station",
            station.toString(),
            "points",
            chargingPoint.toString(),
            "plugs",
            chargingPlug.id.toString()
        )
    ) { return@call it.isSuccessful }

    fun addReview(station: Int, addReviewInfo: AddReview, callback: (Boolean) -> Unit) = call(
        Post(addReviewInfo.getJson().toString()),
        callback,
        arrayOf("review", "create", "station", station.toString())
    ) { return@call it.isSuccessful }

    fun getReviews(station: Int, callback: (JSONArray?) -> Unit) =
        call(
            Get,
            callback,
            arrayOf("review", "all", "station", station.toString())
        ) { return@call if (it.isSuccessful) JSONArray(it.body!!.string()) else null }

    fun addCar(addCarInfo: Car, callback: (Boolean) -> Unit) = call(
        Post(addCarInfo.getJson().toString()),
        callback,
        arrayOf("car", "create")
    ) { return@call it.isSuccessful }

    fun updateCar(addCarInfo: Car, callback: (Boolean) -> Unit) = call(
        Put(addCarInfo.getJson().toString()),
        callback,
        arrayOf("car", "update", addCarInfo.id.toString())
    ) { return@call it.isSuccessful }

    fun removeCar(id: Int, callback: (Boolean) -> Unit) =
        call(
            Delete,
            callback,
            arrayOf("car", "delete", id.toString())
        ) { return@call it.isSuccessful }

    fun getCars(callback: (JSONArray?) -> Unit) =
        call(
            Get,
            callback,
            arrayOf("car", "all")
        ) { return@call if (it.isSuccessful) JSONArray(it.body!!.string()) else null }

    fun forgotPassword(email: String, callback: (Boolean) -> Unit) = call(
        Post(""),
        callback,
        arrayOf("forgot_password"),
        arrayOf(Pair("email", email)),
        useToken = false
    ) { return@call it.isSuccessful }

    fun getTemplateCars(callback: (JSONArray?) -> Unit) =
        call(
            Get,
            callback,
            arrayOf("templatecar", "all")
        ) { return@call if (it.isSuccessful) JSONArray(it.body!!.string()) else null }

    fun isFavourite(station: Int, callback: (Int?) -> Unit) = call(
        Get,
        callback,
        arrayOf("favourite", "all", "station", station.toString())
    ) {
        val json = JSONArray(it.body!!.string())
        if (json.length() > 0) {
            return@call json.getJSONObject(0).getInt("id")
        }
        return@call null
    }

    fun deleteFavourite(station: Int, favourite: Int, callback: (Boolean) -> Unit) = call(
        Delete,
        callback,
        arrayOf("favourite", "delete", favourite.toString(), "station", station.toString())
    ) { return@call it.isSuccessful }

    fun createFavourite(station: Int, callback: (Boolean) -> Unit) = call(
        Post(""),
        callback,
        arrayOf("favourite", "create", "station", station.toString())
    ) { return@call it.isSuccessful }

    fun pay(station: Int, point: Int, plug: Int, totalKw: Int, callback: (String?) -> Unit) = call(
        Post(
            JSONObject().put("totalKW", totalKw).put("description", "ElectroWay payment").toString()
        ),
        callback,
        arrayOf(
            "station",
            station.toString(),
            "points",
            point.toString(),
            "plugs",
            plug.toString(),
            "pay"
        ),
    ) {
        if (it.isSuccessful) {
            val response = it.body!!.string()
            val url = Regex("https://[^\\s]+").find(response)
            if (url != null) {
                return@call url.value
            }
        }
        return@call null
    }
}