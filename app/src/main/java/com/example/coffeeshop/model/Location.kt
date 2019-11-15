package com.example.coffeeshop.model

import com.google.gson.annotations.SerializedName

data class Location(@SerializedName("display_address") val displayAddress: List<String>,
                    @SerializedName("city") val city: String,
                    @SerializedName("country") val country: String,
                    @SerializedName("zip_code") val zipCode: String)
