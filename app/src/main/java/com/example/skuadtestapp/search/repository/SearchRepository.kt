package com.example.skuadtestapp.search.repository

import SearchApiModel
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

class SearchRepository(val context: Context) {

    private lateinit var retrofit: Retrofit
    private lateinit var iJsonPlaceHolderApi: IJsonPlaceHolderApi
    private lateinit var scope: CoroutineScope

    init {
        retrofit = Functions.getInstance().getRetrofitInstance()
        iJsonPlaceHolderApi = retrofit.create(IJsonPlaceHolderApi::class.java)
        scope = CoroutineScope(Dispatchers.Main)
    }

    @ExperimentalCoroutinesApi
    suspend fun getSearchedRestaurants(keyword: String): Flow<SearchApiModel> = channelFlow {
        val call = iJsonPlaceHolderApi.getSearchedRestaurants(keyword = keyword)
        scope.launch {
            withContext(Dispatchers.IO) {
                call.enqueue(object : Callback<SearchApiModel> {
                    override fun onResponse(call: Call<SearchApiModel>, response: Response<SearchApiModel>) {
                        scope.launch {
                            if (response.isSuccessful) {
                                val searchApiModel = response.body()
                                if (searchApiModel != null) {
                                    send(searchApiModel)
                                    close()
                                }

                            } else showToast(context, context.getString(R.string.response_unsuccessful))
                        }
                    }

                    override fun onFailure(call: Call<SearchApiModel>, t: Throwable) {
                        scope.launch {
                            showToast(context, context.getString(R.string.response_no) + t.message)
                        }
                    }

                })
            }
        }
        awaitClose()
    }

}