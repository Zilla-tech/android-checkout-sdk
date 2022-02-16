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

class ZillaRepositoryImpl(private val remoteSource: ZillaRemoteSource) : ZillaRepository {
    override suspend fun validateOrderId(publicKey: String,
                                         orderId: String,): Flow<Result<ValidateOrderIdInfo>> {
        return try {
            flow {
                emit(getAPIResult(remoteSource.validateOrderId(publicKey, orderId)))
            }.catch {
                emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE))
            }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            Logger.log(this, e.toString())
            flow { emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)) }
        }
    }

    override suspend fun createWithPublicKey(publicKey: String, request: CreateWithPublicKeyRequest): Flow<Result<CreateWithPublicKeyInfo>> {
        return try {
            flow {
                emit(getAPIResult(remoteSource.createWithPublicKey(publicKey, request)))
            }.catch {
                emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE))
            }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            Logger.log(this, e.toString())
            flow { emit(Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)) }
        }
    }
}