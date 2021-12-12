package com.zilla.merchantapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.zilla.Zilla
import com.zilla.model.ZillaParams
import com.zilla.ZillaTransactionCallback
import com.zilla.commons.Logger
import com.zilla.model.PaymentInfo
import com.zilla.model.TransactionType
import java.util.*

class MainActivity : AppCompatActivity(), ZillaTransactionCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupZillaGateway()

        findViewById<Button>(R.id.payButton).setOnClickListener {
            completeExistingOrder()
        }
    }

    private fun completeExistingOrder() {
        Zilla.instance.completeExistingOrder(this, "1111111111", this)
    }

    private fun pay() {
        val params =
            ZillaParams.Builder(
                publicKey = "95ced4ff2cde9d5f8537d93016b2d18efe684a4f11a947f2702990f7848120fe",
                amount = 2000)
                .title("Uber ride for Maduekwe Chibuike")
                .clientOrderReference(UUID.randomUUID().toString())
                .redirectUrl("https://zilla-website-dev.zilla.africa/")
                .productCategory("Fashion")
                .build()

        Zilla.instance.createNewOrder(this, params, this)
    }

    private fun setupZillaGateway() {
        val zilla = Zilla.instance
        zilla.transactionType = TransactionType.EXISTING
    }

    override fun onClose() {
        Logger.log(this, "On Close")
    }

    override fun onSuccess(paymentInfo: PaymentInfo) {
        Logger.log(this, "onSuccess $paymentInfo")
    }

    override fun onError() {
    }
}