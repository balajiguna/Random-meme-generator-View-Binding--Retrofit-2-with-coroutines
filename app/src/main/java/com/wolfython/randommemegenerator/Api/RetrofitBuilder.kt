package com.wolfython.randommemegenerator.Api

import com.google.gson.GsonBuilder
import com.wolfython.randommemegenerator.Api.API.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)

    private val mGson = GsonBuilder()
        .setLenient() // Set the non-strict mode of GSON setLenient()
        .create()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .client(httpClient.build())
            .build()
    }

    val apiService: ApiInterface = getRetrofit().create(ApiInterface::class.java)
}