package com.zilla.model

class ZillaParams private constructor(
    val amount: Int,
    val title: String,
    val productCategory: String,
    val redirectUrl: String,
    val clientOrderReference: String,
    val type: String
) {


    data class Builder(
        var amount: Int,
        var title: String = "",
        var productCategory: String = "",
        var redirectUrl: String = "",
        var clientOrderReference: String = "",
        var type: String = ""
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

        fun type(type: String) = apply {
            this.type = type
        }

        fun build() = ZillaParams(
            amount, title, productCategory, redirectUrl, clientOrderReference, type
        )
    }
}