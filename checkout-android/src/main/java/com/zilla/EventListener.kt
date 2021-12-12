package com.zilla

import com.zilla.model.PaymentInfo

interface EventListener {
    fun onClose()
    fun onSuccess(paymentInfo: PaymentInfo)
}