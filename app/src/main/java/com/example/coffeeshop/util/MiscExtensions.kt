package com.example.coffeeshop.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException

inline fun <reified T : Any> classTag(): String = T::class.java.simpleName

/**
 * Easier declaration of initiating the view model within the Activity Scope:
 * viewModel = ViewModelProviders.of(activity).get(ViewModel::class.java)
 */
inline fun <reified VM : ViewModel> Fragment.initViewModel() =
        activity?.run {
            ViewModelProviders.of(this).get(VM::class.java)
        } ?: throw RuntimeException("Invalid Activity in ViewModel Instantiation")

/**
 * Extension for inflating views into a ViewGroup container.
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

/**
 * Easily create a RecyclerView
 */
inline fun <reified VH : RecyclerView.ViewHolder> RecyclerView.initRecyclerView(
        rvLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
        rvAdapter: RecyclerView.Adapter<VH>,
        fixedSize: Boolean = true) =
        with(this) {
            setHasFixedSize(fixedSize)
            layoutManager = rvLayoutManager
            adapter = rvAdapter
        }
