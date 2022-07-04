package com.example.githubprofile.services

import com.example.githubprofile.BuildConfig
import com.example.githubprofile.helpers.Config
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    // buat loggingInterceptor
    private val loggingInterceptor = if(BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    // buat okhttp client
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).addInterceptor(
        loggingInterceptor)

    // buat retrofit builder
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(Config.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(
        okHttp.build())

    // buat retrogit instance
    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}