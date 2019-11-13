package com.example.coffeeshop.network

import android.util.Log
import com.example.coffeeshop.model.SearchResultDTO
import com.example.coffeeshop.network.service.YelpSearchService
import com.example.coffeeshop.network.service.api.YelpSearchApiService
import com.example.coffeeshop.util.Resource
import com.example.coffeeshop.util.classTag
import io.reactivex.Observable
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Data layer responsible for making I/O calls.
 *
 * Implement a layer of caching with a timeout to ensure we aren't making network calls too often.
 * You can modify the threshold via [SEARCH_RESULT_CACHE_THRESHOLD], which is set to 5 minutes by
 * default.
 */
class YelpSearchRepository(yelpSearchApiService: YelpSearchApiService) {

    private var searchService: YelpSearchService = YelpSearchService(yelpSearchApiService)
    private var coffeeShopFetchTimestamp = 0L
    private var cachedSearchResults: SearchResultDTO? = null

    fun getNearbyCoffeeShops(term: String, location: String): Observable<Resource<SearchResultDTO>> =
            Observable.just(Pair(term, location))
                    .flatMap { inputs ->
                        val lastFetchTime = System.currentTimeMillis() - coffeeShopFetchTimestamp
                        val resultDTO = cachedSearchResults
                        if (lastFetchTime < SEARCH_RESULT_CACHE_THRESHOLD && resultDTO != null) {
                            Log.d(TAG, "Returning search results from cache")
                            Observable.just(Resource.success(resultDTO))
                        } else {
                            Log.d(TAG, "Getting search results from network")
                            fetchCoffeeShopsFromNetwork(inputs.first, inputs.second)
                                    .map {
                                        cachedSearchResults = it.data
                                        it
                                    }
                        }
                    }

    private fun fetchCoffeeShopsFromNetwork(term: String,
                                            location: String): Observable<Resource<SearchResultDTO>> =
            Observable.fromCallable {
                searchService.fetchNearbyCoffeeShops(
                        term, location, SEARCH_RADIUS_METERS, SEARCH_LIMIT, SEARCH_SORT_BY)
            }.map {
                val body = it.body()
                if (it.isSuccessful && body != null) {
                    Resource.success(body)
                } else {
                    Log.e(TAG, "Failed to get Coffee Shops: $it")
                    Resource.error(IOException("Failed to get Coffee Shops"), body)
                }
            }

    companion object {
        private val TAG = classTag<YelpSearchRepository>()
        private val SEARCH_RESULT_CACHE_THRESHOLD = TimeUnit.MINUTES.toMillis(5)

        private const val SEARCH_RADIUS_METERS = 4000
        private const val SEARCH_LIMIT = 10
        private const val SEARCH_SORT_BY = "rating"
    }
}
