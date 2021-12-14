package com.zilla.commons

import android.util.Base64

fun String.toBase64() : String {
    val data: ByteArray = this.toByteArray(Charsets.UTF_8)
    return Base64.encodeToString(data, Base64.NO_WRAP)
}