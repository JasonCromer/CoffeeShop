package com.example.coffeeshop.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
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