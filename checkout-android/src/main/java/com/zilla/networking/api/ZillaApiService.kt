package com.zilla.networking.api

import com.zilla.networking.model.request.CreateWithPublicKeyRequest
import com.zilla.networking.model.response.BaseResponse
import com.zilla.networking.model.response.CreateWithPublicKeyInfo
import com.zilla.networking.model.response.ValidateOrderIdInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ZillaApiService {

  @GET(Urls.EP_VALID_FOR_PAYMENT)
  suspend fun validateOrderId(
    @Path("orderCode") orderId: String): Response<BaseResponse<ValidateOrderIdInfo>>


  @POST(Urls.EP_CREATE_WITH_PK)
  suspend fun createWithPublicKey(@Body request: CreateWithPublicKeyRequest):
          Response<BaseResponse<CreateWithPublicKeyInfo>>

}
