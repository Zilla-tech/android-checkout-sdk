package com.zilla.networking.api

interface Urls {
    companion object {
//        const val BASE_URL = "https://bnpl-gateway.usezilla.com/"
        const val BASE_URL = "https://bnpl-gateway-dev.zilla.africa/"
        const val EP_CREATE_WITH_PK = "sdk/bnpl/purchase-order/create-with-pk"
        const val EP_VALID_FOR_PAYMENT = "sdk/bnpl/purchase-order/{orderCode}/valid-for-payment"
    }
}
