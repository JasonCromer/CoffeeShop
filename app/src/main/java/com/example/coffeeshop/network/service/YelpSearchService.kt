package com.example.coffeeshop.network.service

import com.example.coffeeshop.model.SearchResultDTO
import com.example.coffeeshop.network.service.api.YelpSearchApiService
import io.reactivex.exceptions.Exceptions
import retrofit2.Response
import java.io.IOException

/**
 * Service Handling Search Api calls
 *
 * Injecting an instance of [YelpSearchApiService] here would be nicer with Dagger2 instead of
 * passing in one!
 */
class YelpSearchService(private val yelpSearchApiService: YelpSearchApiService) {

    fun fetchNearbyCoffeeShops(term: String, location: String,
                               radius: Int, limit: Int, sortBy: String): Response<SearchResultDTO> =
            try {
                yelpSearchApiService.fetchNearbyCoffeeShops(
                        term, location, radius, limit, sortBy).execute()
            } catch (io: IOException) {
                throw Exceptions.propagate(io)
            }
}
