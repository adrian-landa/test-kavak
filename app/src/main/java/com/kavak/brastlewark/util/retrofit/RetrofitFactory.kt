package com.kavak.brastlewark.util.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.kavak.brastlewark.constans.Web
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {


    @SuppressLint("MissingPermission")
    private fun interceptHeader(context: Context): Interceptor {
        return Interceptor { chain ->
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val hasNetwork = networkInfo != null && networkInfo.isConnectedOrConnecting
            if (!hasNetwork) {
                throw NoInternetException()
            } else {
                chain.proceed(chain.request())
            }

        }
    }

    private fun httpClient(context: Context): OkHttpClient {
        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.i(Web.LOG_API, it)
        })
        logger.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(interceptHeader(context))
            .readTimeout(Web.TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(Web.TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .connectTimeout(Web.TIMEOUT_MS, TimeUnit.MILLISECONDS)
        return okHttp.build()
    }


    fun <T> makeService(urlBase: String, serviceClass: Class<T>, context: Context): T {
        val builder = Retrofit.Builder()
            .baseUrl(urlBase)
            .client(httpClient(context))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return builder.create(serviceClass)
    }

}