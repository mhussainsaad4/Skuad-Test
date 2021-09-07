package com.example.skuadtestapp.home.repository

import NearbyApiModel
import android.content.Context
import com.example.skuadtestapp.R
import com.example.skuadtestapp.network.IJsonPlaceHolderApi
import com.example.skuadtestapp.utils.Functions
import com.example.skuadtestapp.utils.showToast
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeRepository(val context: Context) {

    private var retrofit: Retrofit
    private lateinit var iJsonPlaceHolderApi: IJsonPlaceHolderApi
    private var scope: CoroutineScope

    init {
        retrofit = Functions.getInstance().getRetrofitInstance()
        scope = CoroutineScope(Dispatchers.Main)
    }

    @ExperimentalCoroutinesApi
    suspend fun getNearbyRestaurants(): Flow<NearbyApiModel> = channelFlow {
        iJsonPlaceHolderApi = retrofit.create(IJsonPlaceHolderApi::class.java)
        val call = iJsonPlaceHolderApi.getNearbyRestaurants()

        scope.launch {
            withContext(Dispatchers.IO) {
                call.enqueue(object : Callback<NearbyApiModel> {
                    override fun onResponse(call: Call<NearbyApiModel>, response: Response<NearbyApiModel>) {
                        scope.launch {
                            if (response.isSuccessful) {
                                val nearbyApiModel = response.body()
                                if (nearbyApiModel != null) {
                                    send(nearbyApiModel)
                                    close()                     //now close it
                                }
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
        awaitClose()            //don't close channel flow
    }
}