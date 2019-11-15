package com.example.coffeeshop.coffeesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.coffeeshop.R
import com.example.coffeeshop.coffeesearch.adapter.CoffeeSearchAdapter
import com.example.coffeeshop.util.Resource
import com.example.coffeeshop.util.classTag
import com.example.coffeeshop.util.hideKeyboard
import com.example.coffeeshop.util.initRecyclerView
import com.example.coffeeshop.util.initViewModel
import kotlinx.android.synthetic.main.fragment_coffee_list.*

class CoffeeListFragment : Fragment() {

    private val searchViewModel by lazy { initViewModel<CoffeeSearchViewModel>() }
    private val coffeeSearchAdapter = CoffeeSearchAdapter()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_coffee_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        subscribeToViewModel()
        setListener()
    }

    private fun initAdapter() =
            searchResultsRecyclerView.initRecyclerView(rvAdapter = coffeeSearchAdapter)

    private fun subscribeToViewModel() {
        searchViewModel.searchResultLiveData.observe(this, Observer { result ->
            when (result?.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.let { searchResultDTO ->
                        coffeeSearchAdapter.setAdapterItems(searchResultDTO.businesses)
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(
                            context, R.string.generic_error_message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    return@Observer
                }
            }
        })
    }

    private fun setListener() {
        searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    if (!textView.text.isNullOrBlank()) {
                        searchViewModel.getNearbyCoffeeShops(textView.text.toString())
                        hideKeyboard()
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun hideKeyboard() = activity?.hideKeyboard()

    companion object {
        val TAG = classTag<CoffeeListFragment>()

        fun newInstance() = CoffeeListFragment()
    }
}
