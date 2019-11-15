package com.example.coffeeshop.network.service.api

import com.example.coffeeshop.model.SearchResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpSearchApiService {

    @GET("v3/businesses/search")
    fun fetchNearbyCoffeeShops(@Query("term") searchTerm: String,
                               @Query("location") location: String,
                               @Query("radius") radius: Int,
                               @Query("limit") limit: Int,
                               @Query("sort_by") sortBy: String): Call<SearchResultDTO>
}
