package com.example.skuadtestapp.home.repository

import NearbyApiModel
import android.content.Context
import com.example.skuadtestapp.R
import com.example.skuadtestapp.network.IJsonPlaceHolderApi
import com.example.skuadtestapp.utils.Functions
import com.example.skuadtestapp.utils.showToast
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeRepository(val context: Context) {

    private lateinit var retrofit: Retrofit
    private lateinit var iJsonPlaceHolderApi: IJsonPlaceHolderApi
    private lateinit var scope: CoroutineScope

    init {
        retrofit = Functions.getInstance().getRetrofitInstance()
        iJsonPlaceHolderApi = retrofit.create(IJsonPlaceHolderApi::class.java)
        scope = CoroutineScope(Dispatchers.Main)
    }

    suspend fun getNearbyRestaurants(): Flow<NearbyApiModel> = flow {
        val call = iJsonPlaceHolderApi.getNearbyRestaurants()
        scope.launch {
            withContext(Dispatchers.IO) {
                call.enqueue(object : Callback<NearbyApiModel> {
                    override fun onResponse(call: Call<NearbyApiModel>, response: Response<NearbyApiModel>) {
                        scope.launch {
                            if (response.isSuccessful) {
                                val nearbyApiModel = response.body()
                                if (nearbyApiModel != null)
                                    emit(nearbyApiModel)

                            } else showToast(context, context.getString(R.string.response_unsuccessful))
                        }
                    }

                    override fun onFailure(call: Call<NearbyApiModel>, t: Throwable) {
                        scope.launch {
                            showToast(context, context.getString(R.string.response_no) + t.message)
                        }
                    }

                })
            }
        }
    }
}