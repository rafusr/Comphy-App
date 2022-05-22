package com.comphy.photo.data.source.remote.client

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val okHttpClient: OkHttpClient
) {

    fun instance(baseUrl: String): ApiService {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

fun okHttpClient(
    tokenInterceptor: TokenInterceptor,
    tokenAuthenticator: TokenAuthenticator
): OkHttpClient =
    OkHttpClient.Builder()
        .authenticator(tokenAuthenticator)
        .addInterceptor(tokenInterceptor)
        .addInterceptor(logging)
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()

private val logging: HttpLoggingInterceptor
    get() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }