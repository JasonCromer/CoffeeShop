package com.example.coffeeshop.model

import com.google.gson.annotations.SerializedName

data class Business(@SerializedName("name") val name: String,
                    @SerializedName("price") val price: String,
                    @SerializedName("image_url") val imageUrl: String,
                    @SerializedName("phone") val phoneNumber: String)