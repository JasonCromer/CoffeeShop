package com.example.coffeeshop.coffeesearch.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.model.Business

/**
 * Base ViewHolder in the event we wish to create different ViewHolder types for an adapter
 */
abstract class BaseSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(business: Business)

    internal fun createLocation(locationArray: List<String>) = locationArray.joinToString { it }
}
