package com.mateuscostaluz.presensor.services.retrofit

import androidx.lifecycle.MutableLiveData
import com.mateuscostaluz.presensor.global.Global
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mateuscostaluz.presensor.services.interfaces.RetrofitInterface
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitRef(private var global: Global) {

    private var retrofit: Retrofit? = null

    init {
        if (retrofit == null) {

            val gson: Gson = GsonBuilder()
                .setLenient()
                .create()

            var okHttpClient = OkHttpClient().newBuilder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addInterceptor {
                    var ongoing = it.request().newBuilder();
                    val jwt = (global.jwt as MutableLiveData<String>).value
                    ongoing.addHeader("Accept", "application/json;versions=1");
                    if (jwt != null) {
                        ongoing.addHeader("Authorization", "Bearer ${jwt}");
                    }
                    ongoing.header("Content-Type", "application/json");
                    ongoing.header("Accept", "application/json");
                    return@addInterceptor it.proceed(ongoing.build());
                }
                .build();
            retrofit =
                Retrofit.Builder()
                    .baseUrl("https://presensor.herokuapp.com/")
//                    .baseUrl(
//
//                                    "https://5e32627d-154a-402b-9090-3a362e970185.mock.pstmn.io/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }

    private fun getRetrofit(): Retrofit? {
        return retrofit
    }

    fun getRetrofitInterface(): RetrofitInterface {
        return getRetrofit()!!.create(
            RetrofitInterface::class.java
        )
    }
}