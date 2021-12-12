package com.zilla

import android.webkit.JavascriptInterface
import com.zilla.commons.Constants
import com.zilla.commons.Logger
import com.zilla.model.Event
import com.zilla.model.PaymentInfo
import org.json.JSONException

class ZillaWebInterface(private val eventListener: EventListener) {

    @JavascriptInterface
    @Throws(JSONException::class)
    fun postMessage(message: String) {
        Logger.log(this, "ZillaWebInterface $message")
        val event = Event.fromString(message)
        when (event.type) {
            Constants.EVENT_CLOSED -> { // {"type":"zilla.widget.closed","data":"cancelled"}
                Logger.log(this, "ZILLA EVENT_CLOSED")
                eventListener.onClose()
            }
            Constants.EVENT_COMPLETED_PAYMENT -> {
                Logger.log(this, "ZILLA EVENT_COMPLETED_PAYMENT")

                PaymentInfo.fromJson(event.data)?.let {
                    eventListener.onSuccess(it)
                } ?: run {
                    Logger.log(this, "Zilla EVENT_COMPLETED_PAYMENT: Payment Info null")
                }
            }
        }
    }

}