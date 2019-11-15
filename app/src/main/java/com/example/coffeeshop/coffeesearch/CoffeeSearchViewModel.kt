package com.example.coffeeshop.coffeesearch

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeshop.CoffeeApplication
import com.example.coffeeshop.model.SearchResultDTO
import com.example.coffeeshop.network.YelpSearchRepository
import com.example.coffeeshop.util.Resource
import com.example.coffeeshop.util.classTag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoffeeSearchViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var yelpSearchRepository: YelpSearchRepository

    private val disposable = CompositeDisposable()

    /**
     * Kotlin-idiomatic way to protect public immutability of LiveData
     */
    private val _searchResultLiveData = MutableLiveData<Resource<SearchResultDTO>>()
    val searchResultLiveData: LiveData<Resource<SearchResultDTO>>
        get() = _searchResultLiveData

    init {
        (application as CoffeeApplication).appComponent.inject(this)
    }

    /**
     * Fetches nearby coffee shops from a given location
     */
    fun getNearbyCoffeeShops(location: String) =
            disposable.add(yelpSearchRepository.getNearbyCoffeeShops(location)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { response ->
                        _searchResultLiveData.value = response
                    }
                    .subscribe({
                        Log.d(TAG, "Successfully fetched coffee shops")
                    }, {
                        Log.e(TAG, "Error fetching coffee shops")
                    }))

    override fun onCleared() = disposable.clear()

    companion object {
        private val TAG = classTag<CoffeeSearchViewModel>()
    }
}
