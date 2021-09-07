package com.example.skuadtestapp.utils

import com.example.skuadtestapp.utils.K.NetworkConstants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Functions private constructor() {

    companion object {
        private val functions = Functions()

        @Synchronized
        fun getInstance(): Functions {
            return functions
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }
}