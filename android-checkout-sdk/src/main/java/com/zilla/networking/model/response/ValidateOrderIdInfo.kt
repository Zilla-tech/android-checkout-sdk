package com.zilla.networking.model.response

data class ValidateOrderIdInfo(
    val paymentLink: String?,
    val redirectUrl: String?,
    val validForPayment: Boolean?
)