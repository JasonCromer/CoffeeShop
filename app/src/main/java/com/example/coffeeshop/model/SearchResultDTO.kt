package com.example.coffeeshop.model

import com.google.gson.annotations.SerializedName

data class SearchResultDTO(@SerializedName("businesses") val businesses: List<Business>)
