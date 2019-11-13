package com.example.coffeeshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.coffeeshop.util.Resource
import com.example.coffeeshop.util.classTag
import com.example.coffeeshop.util.initViewModel

class CoffeeListFragment : Fragment() {

    private val searchViewModel by lazy { initViewModel<CoffeeSearchViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_coffee_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        searchViewModel.showDialogEvent.observe(this, Observer {
            when (it?.status) {
                Resource.Status.SUCCESS -> {

                }
                Resource.Status.ERROR -> {

                }
                Resource.Status.LOADING -> {
                    return@Observer
                }
            }
        })

        searchViewModel.getNearbyCoffeeShops("Coffee", "410 Townsend Street, San Francisco, CA")
    }

    companion object {
        val TAG = classTag<CoffeeListFragment>()

        fun newInstance() = CoffeeListFragment()
    }
}
