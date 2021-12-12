package com.zilla.model

import com.zilla.commons.Constants
import org.json.JSONException
import org.json.JSONObject

class Event(var type: String, var data: String?) {

    companion object {
        @Throws(JSONException::class)
        fun fromString(data: String): Event {
            val event = JSONObject(data)
            val type = event.getString("type")
            val body: String? = event.getString("data")
            return Event(type, body)
        }
    }
}