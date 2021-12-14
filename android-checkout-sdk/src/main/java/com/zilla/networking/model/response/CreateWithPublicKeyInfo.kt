package com.zilla.networking.model.response

data class CreateWithPublicKeyInfo(
    val amount: Int? = 0,
    val clientOrderReference: String? = "",
    val completedAt: String?,
    val createdAt: String? = "",
    val createdByPrincipalId: String? = "",
    val customerHandle: String?,
    val customerId: String?,
    val id: String? = "",
    val merchantOutletId: String? = "",
    val orderCode: String? = "",
    val orderSource: String? = "",
    val paymentLink: String? = "",
    val productCategory: String? = "",
    val redirectUrl: String? = "",
    val reusableCount: Int? = 0,
    val status: String? = "",
    val title: String? = "",
    val type: String? = "",
    val usedCount: Int? = 0
)