package com.zilla.networking.model.response

data class BaseResponse<T>(val message: String?, val data: T?)