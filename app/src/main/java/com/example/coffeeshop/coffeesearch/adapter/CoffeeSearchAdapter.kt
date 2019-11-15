package com.example.coffeeshop.coffeesearch.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.model.Business
import com.example.coffeeshop.util.inflate

class CoffeeSearchAdapter : RecyclerView.Adapter<BaseSearchViewHolder>() {

    private var adapterItems = listOf<Business>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSearchViewHolder =
            when (viewType) {
                VIEW_TYPE_COFFEE_RESULT -> {
                    CoffeeItemViewHolder(parent.inflate(R.layout.layout_coffee_search_item))
                }
                else -> {
                    throw IllegalStateException("Cannot create ViewHolder of unknown type")
                }
            }

    override fun onBindViewHolder(holder: BaseSearchViewHolder, position: Int) =
            holder.onBind(adapterItems[position])

    override fun getItemCount() = adapterItems.size

    fun setAdapterItems(businessList: List<Business>) {
        adapterItems = businessList
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_COFFEE_RESULT = 0
    }
}
