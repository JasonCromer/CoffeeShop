package com.example.coffeeshop.dagger

import android.app.Application
import com.example.coffeeshop.network.RetrofitClient
import com.example.coffeeshop.network.service.YelpSearchService
import com.example.coffeeshop.network.service.api.YelpSearchApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication() = application

    @Provides
    @Singleton
    @Named("retrofit_client")
    internal fun provideRetrofit() = RetrofitClient.getInstance()

    @Provides
    @Singleton
    internal fun providesYelpSearchApiService(@Named("retrofit_client") retrofit: Retrofit) =
            retrofit.create<YelpSearchApiService>(YelpSearchApiService::class.java)

    @Provides
    @Singleton
    internal fun providesYelpSearchService(yelpSearchApiService: YelpSearchApiService) =
            YelpSearchService(yelpSearchApiService)
}
