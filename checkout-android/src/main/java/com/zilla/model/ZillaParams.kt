package com.zilla.model

import com.zilla.ZillaTransactionCallback

class ZillaParams private constructor(
    val publicKey: String,
    val amount: Int,
    val title: String,
    val productCategory: String,
    val redirectUrl: String,
    val clientOrderReference: String) {


    data class Builder(
        var publicKey: String,
        var amount: Int,
        var title: String = "",
        var productCategory: String = "",
        var redirectUrl: String = "",
        var clientOrderReference: String = ""
    ) {
        fun title(title: String) = apply {
            this.title = title
        }

        fun productCategory(productCategory: String) = apply {
            this.productCategory = productCategory
        }

        fun redirectUrl(redirectUrl: String) = apply {
            this.redirectUrl = redirectUrl
        }

        fun clientOrderReference(reference: String) = apply {
            this.clientOrderReference = reference
        }

        fun build() = ZillaParams(
            publicKey, amount, title,
            productCategory, redirectUrl, clientOrderReference)
    }
}