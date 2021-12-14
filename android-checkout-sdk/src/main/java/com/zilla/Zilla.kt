package com.zilla

import android.content.Context
import android.content.Intent
import androidx.annotation.Keep
import com.zilla.commons.Logger
import com.zilla.model.ApplicationMode
import com.zilla.model.TransactionType
import com.zilla.model.ZillaParams
import com.zilla.view.ZillaActivity

class Zilla {
    internal lateinit var applicationMode: ApplicationMode
    lateinit var transactionType: TransactionType
    internal lateinit var zillaParams: ZillaParams
    internal lateinit var orderId: String
    internal lateinit var publicKey: String
    internal lateinit var callback: ZillaTransactionCallback

    init {
        if (zilla != null) {
            throw RuntimeException("Use getInstance() method to get the single instance of this class.")
        }
    }

    @Keep
    companion object {
        @Volatile
        private var zilla: Zilla? = null

        val instance: Zilla
            get() {
                if (zilla == null) {
                    synchronized(Zilla::class.java) {
                        if (zilla == null)
                            zilla = Zilla()
                    }
                }
                return zilla!!
            }

    }

    fun completeExistingOrder(
        context: Context,
        publicKey: String,
        orderId: String,
        callback: ZillaTransactionCallback
    ) {

        val zilla = instance
        zilla.transactionType = TransactionType.EXISTING
        zilla.orderId = orderId
        zilla.callback = callback
        zilla.publicKey = publicKey
        openZillaActivity(context)
    }

    fun createNewOrder(
        context: Context,
        publicKey: String,
        params: ZillaParams,
        callback: ZillaTransactionCallback
    ) {

        val zilla = instance
        zilla.transactionType = TransactionType.NEW
        zilla.zillaParams = params
        zilla.callback = callback
        zilla.publicKey = publicKey
        openZillaActivity(context)
    }

    private fun openZillaActivity(
        context: Context
    ) {
        val intent: Intent?

        try {
            intent = Intent(context, ZillaActivity::class.java)
            context.startActivity(intent)
        } catch (e: ClassNotFoundException) {
            Logger.log(this, e.toString())
        }
    }

}