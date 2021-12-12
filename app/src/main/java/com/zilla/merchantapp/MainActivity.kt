package com.zilla.merchantapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import com.zilla.Zilla
import com.zilla.model.ZillaParams
import com.zilla.ZillaTransactionCallback
import com.zilla.commons.Logger
import com.zilla.model.ErrorType
import com.zilla.model.PaymentInfo
import com.zilla.model.TransactionType
import java.util.*

class MainActivity : AppCompatActivity(), ZillaTransactionCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.payButton).setOnClickListener {
            startNewOrder()
        }
    }

    private fun completeExistingOrder() {
        Zilla.instance.completeExistingOrder(this, "1111111111", this)
    }

    private fun startNewOrder() {
        val params =
            ZillaParams.Builder(
                publicKey = "PK_DEV_f19788227b5293e06aecc54844b3faf1b8e206a932f4d2a4901f2195c4840096",
                amount = 2000)
                .title("Uber ride for Maduekwe Chibuike")
                .clientOrderReference(UUID.randomUUID().toString().substring(0,20))
                .redirectUrl("https://zilla-website-dev.zilla.africa/")
                .productCategory("Fashion")
                .build()

        Zilla.instance.createNewOrder(this, params, this)
    }


    override fun onClose() {
        Logger.log(this, "On Close")
    }

    override fun onSuccess(paymentInfo: PaymentInfo) {
        Logger.log(this, "onSuccess $paymentInfo")
    }

    override fun onError(errorType: ErrorType) {
        Logger.log(this, "On Error $errorType")
    }
}