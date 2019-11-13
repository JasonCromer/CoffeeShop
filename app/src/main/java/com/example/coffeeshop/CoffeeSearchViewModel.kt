package com.example.coffeeshop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeeshop.model.SearchResultDTO
import com.example.coffeeshop.network.RetrofitClient
import com.example.coffeeshop.network.YelpSearchRepository
import com.example.coffeeshop.network.service.YelpSearchService
import com.example.coffeeshop.network.service.api.YelpSearchApiService
import com.example.coffeeshop.util.Resource
import com.example.coffeeshop.util.classTag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoffeeSearchViewModel() : ViewModel() {

    private var yelpSearchRepository: YelpSearchRepository =
            YelpSearchRepository(RetrofitClient.getInstance().create(YelpSearchApiService::class.java))
    private val disposable = CompositeDisposable()

    private val _showDialogEvent = MutableLiveData<Resource<SearchResultDTO>>()
    val showDialogEvent: LiveData<Resource<SearchResultDTO>>
        get() = _showDialogEvent

    fun getNearbyCoffeeShops(term: String, location: String) =
            disposable.add(yelpSearchRepository.getNearbyCoffeeShops(term, location)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { response ->
                        _showDialogEvent.value = response
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
