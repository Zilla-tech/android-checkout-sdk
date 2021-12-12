package com.zilla.networking.api

import com.zilla.commons.Logger
import com.zilla.networking.model.request.CreateWithPublicKeyRequest
import com.zilla.networking.model.response.CreateWithPublicKeyInfo
import com.zilla.networking.model.response.ValidateOrderIdInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ZillaRepositoryImpl(private val apiService: ZillaApiService) : ZillaRepository {
    override suspend fun validateOrderId(orderId: String): Flow<Result<ValidateOrderIdInfo>> {
        Logger.log(this, "ZillaRepositoryImpl validateOrderId called")

        return try {
            flow {
                emit(getAPIResult(apiService.validateOrderId(orderId)))
            }.catch {
                emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE))
            }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            flow { emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)) }
        }
    }

    override suspend fun createWithPublicKey(request: CreateWithPublicKeyRequest): Flow<Result<CreateWithPublicKeyInfo>> {
        Logger.log(this, "ZillaRepositoryImpl createWithPublicKey called")

        return try {
            flow {
                emit(getAPIResult(apiService.createWithPublicKey(request)))
            }.catch {
                emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE))
            }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            flow { emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)) }
        }
    }
}