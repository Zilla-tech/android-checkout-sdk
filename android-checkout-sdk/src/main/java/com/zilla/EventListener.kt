package com.zilla

import com.zilla.model.PaymentInfo

interface EventListener {
    fun onClose()
    fun onRequestCameraPermission()
    fun onSuccess(paymentInfo: PaymentInfo)
}