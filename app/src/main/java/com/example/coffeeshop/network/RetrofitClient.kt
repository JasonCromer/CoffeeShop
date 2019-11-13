package com.example.coffeeshop.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A Singleton Retrofit Client.
 *
 * Using Dependency Injection would make this a lot cleaner!
 */
object RetrofitClient {

    private const val BASE_URL = "https://api.yelp.com/"

    // I know... I hate it too, but it's just for the sake of this project.
    private const val API_TOKEN = "gEvpbKnzOhJS6tkrDzv9NySF3YWt9T9NiGrDBf1ISLKhZaTu" +
            "kqp1RAL3KUt1k3DOJxJ1_yiJYDWACMcTSnpBdtpmZ6otCypOWBHfi59oOmCsNyY4DFKzWF-4jXjLXXYx"
    private var retrofitClient: Retrofit? = null

    /**
     * Interceptor to handle Authorization header
     */
    private var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_TOKEN")
                .build()
        chain.proceed(newRequest)
    }.build()

    fun getInstance(): Retrofit = retrofitClient ?: Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
}
