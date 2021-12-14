package com.zilla.networking.api

import com.zilla.networking.model.request.CreateWithPublicKeyRequest
import com.zilla.networking.model.response.BaseResponse
import com.zilla.networking.model.response.CreateWithPublicKeyInfo
import com.zilla.networking.model.response.ValidateOrderIdInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ZillaRemoteSource(private val zillaApiService: ZillaApiService) {

    suspend fun validateOrderId(publicKey: String, orderId: String):
            Response<BaseResponse<ValidateOrderIdInfo>> {
        return withContext(Dispatchers.IO) {
            zillaApiService.validateOrderId(publicKey, orderId)
        }
    }

    suspend fun createWithPublicKey(publicKey: String, request: CreateWithPublicKeyRequest): Response<BaseResponse<CreateWithPublicKeyInfo>> {
        return withContext(Dispatchers.IO) {
            zillaApiService.createWithPublicKey(publicKey, request)
        }
    }
}
