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
    suspend fun getNearbyRestaurants(
        @Query("location") location: String = "47.6204,-122.3491",
        @Query("radius") radius: String = "2500",
        @Query("type") type: String = "restaurant",
        @Query("key") key: String = "AIzaSyDxVclNSQGB5WHAYQiHK-VxYKJelZ_9mjk"
    ): Call<NearbyApiModel>

    @GET(K.NetworkConstants.SEARCH_API)
    suspend fun getSearchedRestaurants(
        @Query("location") location: String = "47.6204,-122.3491",
        @Query("radius") radius: String = "2500",
        @Query("type") type: String = "restaurant",
        @Path("keyword") keyword: String,
        @Query("key") key: String = "AIzaSyDxVclNSQGB5WHAYQiHK-VxYKJelZ_9mjk"
    ): Call<SearchApiModel>
}