package com.enkodo.bookstore.api

import android.content.Context
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class ApiProduction(context: Context) {
    private val url: String = "https://raw.githubusercontent.com/ejgteja/files/main/"
    private var mContext: Context = context


    private fun provideRestAdapter(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(OkHttpProduction.getOkHttpClient(mContext, true))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun <S> provideService(serviceClass: Class<S>): S {
        return provideRestAdapter().create(serviceClass)
    }

}