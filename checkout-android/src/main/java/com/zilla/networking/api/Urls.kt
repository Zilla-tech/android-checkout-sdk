package com.zilla.networking.api

interface Urls {
    companion object {
        const val EP_CREATE_WITH_PK = "purchase-order/create-with-pk"
        const val EP_VALID_FOR_PAYMENT = "purchase-order/{orderCode}/valid-for-payment"
    }
}
