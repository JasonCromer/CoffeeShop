package com.example.coffeeshop.network

import android.util.Log
import com.example.coffeeshop.model.SearchResultDTO
import com.example.coffeeshop.network.service.YelpSearchService
import com.example.coffeeshop.util.Resource
import com.example.coffeeshop.util.classTag
import io.reactivex.Observable
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data layer responsible for making I/O calls.
 *
 * Implement a layer of caching with a timeout to ensure we aren't making network calls too often.
 * You can modify the threshold via [SEARCH_RESULT_CACHE_THRESHOLD], which is set to 30 seconds by
 * default. This type of caching is mostly useful for data we would fetch in larger intervals,
 * i.e. 30 minutes, 4 hours, 3 days, but its here because... bonus points.
 */
@Singleton
class YelpSearchRepository @Inject constructor(private val yelpSearchService: YelpSearchService) {

    private var coffeeShopFetchTimestamp = 0L
    private var cachedSearchResults: SearchResultDTO? = null
    private var cachedSearchLocationInput: String? = null

    /**
     * Fetches nearby coffee shops from a given location. Fetch from cache when we exceed the
     * threshold, and the same location is used.
     */
    fun getNearbyCoffeeShops(location: String): Observable<Resource<SearchResultDTO>> =
            Observable.just(Pair(SEARCH_TERM, location))
                    .flatMap { inputs ->
                        val lastFetchTime = System.currentTimeMillis() - coffeeShopFetchTimestamp
                        val resultDTO = cachedSearchResults

                        if (lastFetchTime < SEARCH_RESULT_CACHE_THRESHOLD
                                && inputs.second == cachedSearchLocationInput
                                && resultDTO != null) {
                            Log.d(TAG, "Returning search results from cache")
                            Observable.just(Resource.success(resultDTO))
                        } else {
                            Log.d(TAG, "Getting search results from network")
                            fetchCoffeeShopsFromNetwork(inputs.first, inputs.second)
                                    .map {
                                        cachedSearchResults = it.data
                                        cachedSearchLocationInput = inputs.second
                                        it
                                    }
                        }
                    }

    private fun fetchCoffeeShopsFromNetwork(term: String,
                                            location: String): Observable<Resource<SearchResultDTO>> =
            Observable.fromCallable {
                yelpSearchService.fetchNearbyCoffeeShops(
                        term, location, SEARCH_RADIUS_METERS, SEARCH_LIMIT, SEARCH_SORT_BY)
            }.map {
                val body = it.body()

                if (it.isSuccessful && body != null) {
                    coffeeShopFetchTimestamp = System.currentTimeMillis()
                    Resource.success(body)
                } else {
                    Log.e(TAG, "Failed to get Coffee Shops: $it")
                    Resource.error(IOException("Failed to get Coffee Shops"))
                }
            }

    companion object {
        private val TAG = classTag<YelpSearchRepository>()
        private val SEARCH_RESULT_CACHE_THRESHOLD = TimeUnit.SECONDS.toMillis(30)

        // We can easily surface these for customization in the UI at any point
        private const val SEARCH_RADIUS_METERS = 4000
        private const val SEARCH_LIMIT = 10
        private const val SEARCH_SORT_BY = "rating"
        private const val SEARCH_TERM = "Coffee"
    }
}
