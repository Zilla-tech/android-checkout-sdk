package com.zilla.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zilla.commons.BigDecimalAdapter
import com.zilla.networking.api.Urls
import com.zilla.networking.api.ZillaApiService
import com.zilla.networking.api.ZillaRemoteSource
import com.zilla.networking.api.ZillaRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class AppContainer {

    private val zillaApiService = createZillaAPI()
    private val remoteSource = ZillaRemoteSource(zillaApiService)

    val zillaRepository = ZillaRepositoryImpl(remoteSource)

    private val httpClientBuilder: OkHttpClient.Builder
        get() {
            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(logging)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .networkInterceptors().add(Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
//                    requestBuilder.header("Content-Type", "application/json")
                    chain.proceed(requestBuilder.build())
                })

            return httpClient
        }

    private val moshi: Moshi
        get() {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(BigDecimalAdapter)
                .build()
        }

    private fun getRetrofitClient(apiBaseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClientBuilder.build())
            .build()
    }

    private fun createZillaAPI(): ZillaApiService {
        val baseURL = Urls.BASE_URL
        return getRetrofitClient(baseURL).create(ZillaApiService::class.java)
    }

}