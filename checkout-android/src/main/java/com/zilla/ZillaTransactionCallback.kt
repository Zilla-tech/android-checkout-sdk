package com.zilla

import com.zilla.model.ErrorType
import com.zilla.model.PaymentInfo

interface ZillaTransactionCallback {
    fun onClose()

    fun onSuccess(paymentInfo: PaymentInfo)

    fun onError(errorType: ErrorType)
}