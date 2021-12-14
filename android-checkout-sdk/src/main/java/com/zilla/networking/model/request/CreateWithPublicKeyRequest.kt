package com.zilla.networking.model.request

import com.zilla.model.ZillaParams

data class CreateWithPublicKeyRequest(
    val amount: String,
    val clientOrderReference: String,
    val productCategory: String,
    val type: String,
    val redirectUrl: String,
    val title: String
) {
    companion object {

        fun fromZillaParams(params: ZillaParams): CreateWithPublicKeyRequest {
            return CreateWithPublicKeyRequest(
                params.amount.toString(),
                params.clientOrderReference,
                params.productCategory,
                params.type,
                params.redirectUrl,
                params.title
            )
        }
    }
}