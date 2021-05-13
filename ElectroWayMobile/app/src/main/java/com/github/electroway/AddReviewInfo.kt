package com.github.electroway

import org.json.JSONObject

class AddReviewInfo(val rating: Int, val text: String) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("rating", rating)
            .put("textReview", text)
    }
}