package com.zilla.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zilla.Zilla
import com.zilla.commons.Logger
import com.zilla.commons.toBase64
import com.zilla.model.ErrorType
import com.zilla.model.TransactionType
import com.zilla.model.ZillaParams
import com.zilla.networking.api.Result
import com.zilla.networking.api.ZillaRepository
import com.zilla.networking.model.request.CreateWithPublicKeyRequest
import com.zilla.networking.model.response.CreateWithPublicKeyInfo
import com.zilla.networking.model.response.ValidateOrderIdInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ZillaViewModel(private val zillaRepository: ZillaRepository) : ViewModel() {

    private val _loadingStatus = MutableSharedFlow<Boolean>()
    val loadingStatus = _loadingStatus.asSharedFlow()

    private val _errorStatus = MutableSharedFlow<ErrorType>()
    val errorStatus = _errorStatus.asSharedFlow()

    private val _orderValidationSuccessful = MutableSharedFlow<ValidateOrderIdInfo>()
    val orderValidationSuccessful = _orderValidationSuccessful.asSharedFlow()

    private val _createWithPublicKeyInfo = MutableSharedFlow<CreateWithPublicKeyInfo>()
    val createWithPublicKeyInfo = _createWithPublicKeyInfo.asSharedFlow()

    fun initiateTransaction() {
        Logger.log(this, "initiateTransaction called")

        if (Zilla.instance.transactionType == TransactionType.NEW) {
            createWithPublicKey(Zilla.instance.zillaParams)
        } else {
            verifyOrderId(Zilla.instance.orderId)
        }
    }

    private fun verifyOrderId(orderId: String) {

        val publicKey = Zilla.instance.publicKey.toBase64()
        Logger.log(this, "Encoded public key $publicKey")

        viewModelScope.launch {
            Logger.log(this, "verifyPayment called")

            _loadingStatus.emit(true)
            zillaRepository.validateOrderId(publicKey, orderId).collect {
                _loadingStatus.emit(false)
                when (it) {
                    is Result.Success -> {
                        if (it.data.validForPayment == true) {
                            _orderValidationSuccessful.emit(it.data)
                        } else {
                            // Show error order is invalid.
                            Logger.log(this, "Order code not valid")
                            _errorStatus.emit(ErrorType.ORDER_ID_NOT_VALID)
                        }
                    }
                    is Result.Error -> {
                        // Show error
                        Logger.log(this, "Error occurred ${it.errorMessage}")
                        _errorStatus.emit(ErrorType.UNKNOWN_ERROR)
                    }
                }

            }
        }
    }

    private fun createWithPublicKey(params: ZillaParams) {

        Logger.log(this, "createWithPublicKey called $params")

        val request = CreateWithPublicKeyRequest.fromZillaParams(params)
        val publicKey = Zilla.instance.publicKey.toBase64()
        Logger.log(this, "Encoded Public key $publicKey")

        viewModelScope.launch {

            _loadingStatus.emit(true)
            zillaRepository.createWithPublicKey(publicKey, request).collect {
                    _loadingStatus.emit(false)
                    when (it) {
                        is Result.Success -> {
                            _createWithPublicKeyInfo.emit(it.data)
                        }
                        is Result.Error -> {
                            // Show error
                            Logger.log(this, "Error occurred ${it.errorMessage}")
                            _errorStatus.emit(ErrorType.UNKNOWN_ERROR)
                        }
                    }
                }
        }
    }
}