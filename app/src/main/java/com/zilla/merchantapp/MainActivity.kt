package com.zilla.merchantapp

import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.zilla.Zilla
import com.zilla.ZillaTransactionCallback
import com.zilla.commons.Logger
import com.zilla.model.ErrorType
import com.zilla.model.PaymentInfo
import com.zilla.model.ZillaParams
import java.util.*

class MainActivity : AppCompatActivity(), ZillaTransactionCallback {

    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.payButton).setOnClickListener {
            startNewOrder()
        }

        root = findViewById(R.id.root)
        prefill()
    }

    private fun completeExistingOrder() {
        Zilla.instance.completeExistingOrder(
            this,
            "PK_DEV_a6b0cdb980c48e80c8a8187330b88fc47cfb1165d999efc0188700ae7aafd6c3",
            "1111111111", this)
    }

    private fun startNewOrder() {
        val amount = findViewById<TextInputEditText>(R.id.amount_text_field)
            .text.toString().toIntOrNull() ?: 0

        val title = findViewById<TextInputEditText>(R.id.merchant_order_title).text.toString()

        val params =
            ZillaParams.Builder(amount = amount)
                .title(title)
                .clientOrderReference(UUID.randomUUID().toString().substring(0, 20))
                .redirectUrl("https://zilla-website-dev.zilla.africa/")
                .productCategory("Fashion")
                .build()

        Zilla.instance.createNewOrder(
            this,
            "PK_DEV_a6b0cdb980c48e80c8a8187330b88fc47cfb1165d999efc0188700ae7aafd6c3",
            params, this
        )
    }

    override fun onClose() {
        Logger.log(this, "On Close")
        showMessage("Closed")
    }

    override fun onSuccess(paymentInfo: PaymentInfo) {
        Logger.log(this, "onSuccess $paymentInfo")
        showMessage("Payment successful")
    }

    override fun onError(errorType: ErrorType) {
        Logger.log(this, "On Error $errorType")
        showMessage(errorType.name.lowercase())
    }

    private fun showMessage(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun prefill() {
        findViewById<TextInputEditText>(R.id.amount_text_field).setText("5000")
        findViewById<TextInputEditText>(R.id.merchant_order_title).setText("Uber for Tolu")
    }
}