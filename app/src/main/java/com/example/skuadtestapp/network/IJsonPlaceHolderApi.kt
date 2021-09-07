package com.example.skuadtestapp.network

import NearbyApiModel
import SearchApiModel
import com.example.skuadtestapp.utils.K
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IJsonPlaceHolderApi {

    @GET(K.NetworkConstants.NEARBY_API)
    fun getNearbyRestaurants(
        @Query("location") location: String = "31.476181707050397, 74.28104322630328",
        @Query("radius") radius: String = "2500",
        @Query("type") type: String = "restaurant",
        @Query("key") key: String = "AIzaSyDxVclNSQGB5WHAYQiHK-VxYKJelZ_9mjk"
    ): Call<NearbyApiModel>                 //For using Retrofit Call, don't make these api calls as suspend

    @GET(K.NetworkConstants.SEARCH_API)
    fun getSearchedRestaurants(
        @Query("location") location: String = "31.476181707050397, 74.28104322630328",
        @Query("radius") radius: String = "2500",
        @Query("type") type: String = "restaurant",
        @Query("keyword") keyword: String,
        @Query("key") key: String = "AIzaSyDxVclNSQGB5WHAYQiHK-VxYKJelZ_9mjk"
    ): Call<SearchApiModel>
}