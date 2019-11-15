package com.example.coffeeshop.coffeesearch.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.example.coffeeshop.R
import com.example.coffeeshop.model.Business
import kotlinx.android.synthetic.main.layout_coffee_search_item.view.*

class CoffeeItemViewHolder(itemView: View) : BaseSearchViewHolder(itemView) {

    override fun onBind(business: Business): Unit =
            itemView.run {
                coffeeSearchItemTitle.text = business.name
                coffeeSearchItemAddress.text = createLocation(business.location.displayAddress)
                coffeeSearchItemPrice.text = business.price
                Glide.with(context).load(business.imageUrl)
                        .placeholder(R.drawable.ic_coffee).centerCrop().into(coffeeSearchItemImage)
            }

    private fun createLocation(locationArray: List<String>) = locationArray.joinToString { it }
}
