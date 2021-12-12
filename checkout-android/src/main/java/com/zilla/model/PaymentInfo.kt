package com.zilla.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

data class PaymentInfo(
    val clientOrderReference: String = "",
    val status: String = "",
    val zillaOrderCode: String = ""
) {
    companion object {
        fun fromJson(json: String?): PaymentInfo? {
            val moshi: Moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<PaymentInfo> = moshi.adapter(PaymentInfo::class.java)

             return jsonAdapter.fromJson(json)
        }
    }
}