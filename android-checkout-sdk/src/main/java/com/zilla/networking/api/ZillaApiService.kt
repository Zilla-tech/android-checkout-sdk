package com.zilla.networking.api

import com.zilla.networking.model.request.CreateWithPublicKeyRequest
import com.zilla.networking.model.response.BaseResponse
import com.zilla.networking.model.response.CreateWithPublicKeyInfo
import com.zilla.networking.model.response.ValidateOrderIdInfo
import retrofit2.Response
import retrofit2.http.*

interface ZillaApiService {

    @GET(Urls.EP_VALID_FOR_PAYMENT)
    suspend fun validateOrderId(
        @Header("public-key") publicKey: String,
        @Path("orderCode") orderId: String
    ): Response<BaseResponse<ValidateOrderIdInfo>>


    @POST(Urls.EP_CREATE_WITH_PK)
    suspend fun createWithPublicKey(
        @Header("public-key") publicKey: String,
        @Body request: CreateWithPublicKeyRequest
    ): Response<BaseResponse<CreateWithPublicKeyInfo>>
}
