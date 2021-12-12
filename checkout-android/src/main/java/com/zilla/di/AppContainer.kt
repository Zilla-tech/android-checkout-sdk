package com.zilla.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zilla.model.Environment
import com.zilla.networking.api.ZillaApiService
import com.zilla.networking.api.ZillaRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class AppContainer(environment: Environment) {

    private val zillaApiService = createZillaAPI(environment)

    val zillaRepository = ZillaRepositoryImpl(zillaApiService)

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
                    requestBuilder.header("Content-Type", "application/json")
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

    private fun createZillaAPI(environment: Environment): ZillaApiService {
        val baseURL = environment.url
        return getRetrofitClient(baseURL).create(ZillaApiService::class.java)
    }

}