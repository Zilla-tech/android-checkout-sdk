package com.zilla.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class PaymentInfo(
    val clientOrderReference: String?,
    val status: String?,
    val zillaOrderCode: String?
) {
    companion object {
        fun fromJson(json: String): PaymentInfo? {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<PaymentInfo> = moshi.adapter(PaymentInfo::class.java)

             return jsonAdapter.fromJson(json)
        }
    }
}